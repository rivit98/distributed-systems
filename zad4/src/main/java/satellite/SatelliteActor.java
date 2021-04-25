package satellite;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.extern.slf4j.Slf4j;
import messages.satellite.ISatelliteMessage;
import messages.satellite.SatelliteQuery;
import messages.satellite.SatelliteResponse;

@Slf4j
public class SatelliteActor extends AbstractBehavior<ISatelliteMessage> {

    public SatelliteActor(ActorContext<ISatelliteMessage> context) {
        super(context);
    }

    public static Behavior<ISatelliteMessage> create() {
        return Behaviors.setup(SatelliteActor::new);
    }

    @Override
    public Receive<ISatelliteMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(SatelliteQuery.class, this::onStatusRequest)
                .build();
    }

    private Behavior<ISatelliteMessage> onStatusRequest(SatelliteQuery query) {
        var status = SatelliteAPI.getStatus(query.getSatelliteID());

        var response = new SatelliteResponse(query.getQueryID(), query.getSatelliteID(), status);
        query.getReplyTo().tell(response);

//        log.info(response.toString());

        return Behaviors.stopped();
    }
}
