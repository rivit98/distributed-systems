package messages;

import akka.actor.typed.ActorRef;
import lombok.Data;

@Data
public class ErrorStatsQuery implements IMessage {
    private final int satelliteID;
    private ActorRef<IMessage> replyTo;
}
