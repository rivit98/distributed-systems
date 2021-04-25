package messages;

import lombok.Data;

@Data
public class ErrorStatsResponse implements IMessage {
    private final int satelliteID;
    private final int errorCount;
}
