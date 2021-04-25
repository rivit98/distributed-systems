package messages.db;

import akka.actor.typed.ActorRef;
import lombok.Data;
import messages.IMessage;

@Data
public class ErrorStatsQuery implements IDbMessage, IMessage {
    private final int satelliteID;
    private ActorRef<IMessage> replyTo;
}
