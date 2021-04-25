package satellite;

import akka.actor.typed.Behavior;
import akka.actor.typed.DispatcherSelector;
import akka.actor.typed.javadsl.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import messages.IMessage;
import messages.QueryResults;
import messages.StationQuery;
import messages.Timeout;
import messages.satellite.ISatelliteMessage;
import messages.satellite.SatelliteQuery;
import messages.satellite.SatelliteResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Getter
public class SatellitesSupervisor extends AbstractBehavior<IMessage> {
    private StationQuery originalQuery;
    private List<CompletableFuture<SatelliteResponse>> futureList;

    public SatellitesSupervisor(ActorContext<IMessage> context) {
        super(context);
    }

    public static Behavior<IMessage> create() {
        return Behaviors.setup(SatellitesSupervisor::new);
    }

    @Override
    public Receive<IMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(StationQuery.class, this::onQuery)
                .onMessage(Timeout.class, this::onTimeout)
                .build();
    }

    private Behavior<IMessage> onTimeout(Timeout timeout) {
        var responsesOnTime =
                futureList
                        .stream()
                        .filter(future -> future.isDone() && !future.isCompletedExceptionally())
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList());

        var percent = ((double) responsesOnTime.size() / originalQuery.getRange()) * 100.0;
        var errorsMap =
                responsesOnTime
                        .stream()
                        .filter(res -> res.getStatus() != SatelliteAPI.Status.OK)
                        .collect(Collectors.toMap(
                                SatelliteResponse::getSatelliteID,
                                SatelliteResponse::getStatus
                        ));

        var response = new QueryResults(
                originalQuery.getQueryID(),
                errorsMap,
                percent
        );

        originalQuery.getReplyTo().tell(response);
        return Behaviors.stopped();
    }

    private Behavior<IMessage> onQuery(StationQuery query) {
        originalQuery = query;

        var startID = query.getFirstSatID();
        var endID = startID + query.getRange();
        futureList = IntStream
                .range(startID, endID)
                .mapToObj(id -> {
                    var satelliteActor = getContext().spawn(
                            SatelliteActor.create(),
                            "satellite-actor-" + query.getQueryID() + "-" + id,
                            DispatcherSelector.fromConfig("my-dispatcher")
                    );

                    return AskPattern.<ISatelliteMessage, SatelliteResponse>ask(
                            satelliteActor,
                            replyTo -> new SatelliteQuery(query.getQueryID(), id, replyTo),
                            query.getTimeout(),
                            getContext().getSystem().scheduler()
                    )
                            .toCompletableFuture();

                })
                .collect(Collectors.toList());

        return Behaviors.withTimers(timers -> {
            timers.startSingleTimer(new Timeout(), query.getTimeout());
//            timers.startSingleTimer(new Timeout(), query.getTimeout().multipliedBy(2));
            return this;
        });
    }
}
