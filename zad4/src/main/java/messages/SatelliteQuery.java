package messages;

import akka.actor.typed.ActorRef;
import lombok.Data;

@Data
public class SatelliteQuery implements ISatelliteMessage{
    private final int queryID;
    private final int satelliteID;
    private final ActorRef<SatelliteResponse> replyTo;
}
