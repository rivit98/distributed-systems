package messages;

import akka.actor.typed.ActorRef;
import lombok.Data;
import lombok.ToString;

import java.time.Duration;

@Data
public class StationQuery implements IMessage{
    private int queryID;
    private final int firstSatID;
    private final int range;
    private final Duration timeout;
    @ToString.Exclude private ActorRef<IMessage> replyTo;
}
