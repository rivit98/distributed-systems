package station;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.extern.slf4j.Slf4j;
import messages.IMessage;
import messages.QueryResults;
import messages.StationQuery;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Station extends AbstractBehavior<IMessage> {
    private int queryID = 1;
    private final String name;
    private final ActorRef<IMessage> dispatcher;
    private final Map<Integer, Duration> pending = new HashMap<>();

    public Station(ActorContext<IMessage> context, ActorRef<IMessage> dispatcher) {
        super(context);

        this.name = getContext().getSelf().path().name();
        this.dispatcher = dispatcher;
    }

    public static Behavior<IMessage> create(ActorRef<IMessage> dispatcher) {
        return Behaviors.setup(context -> new Station(context, dispatcher));
    }

    @Override
    public Receive<IMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(StationQuery.class, this::onStationQuery)
                .onMessage(QueryResults.class, this::onStationResponse)
                .build();
    }

    private Behavior<IMessage> onStationResponse(QueryResults response) {
        var endTime = Duration.ofMillis(System.currentTimeMillis());
        var requestTime = endTime.minus(pending.remove(response.getQueryID()));
        var errors = response.getErrors();
        var sb = new StringBuilder(
                String.format(
                        "%s, queryID: %d, response time: %dms, errors: %d, response percent %.2f%%",
                        name,
                        response.getQueryID(),
                        requestTime.toMillis(),
                        errors.size(),
                        response.getPercent())
        );

        errors.forEach((k, v) -> sb.append('\n').append(k).append(": ").append(v));
        log.info(sb.toString());

        return this;
    }

    public Behavior<IMessage> onStationQuery(StationQuery query){
        query.setReplyTo(getContext().getSelf());
        query.setQueryID(queryID);
        log.info("[" + name + "] " + query);
        dispatcher.tell(query);
        pending.put(queryID, Duration.ofMillis(System.currentTimeMillis()));
        queryID++;
        return this;
    }
}
