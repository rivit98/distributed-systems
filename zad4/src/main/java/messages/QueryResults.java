package messages;

import lombok.Data;
import satellite.SatelliteAPI;

import java.util.Map;

@Data
public class QueryResults implements IMessage {
    private final int queryID;
    private final Map<Integer, SatelliteAPI.Status> errors ;
    private final double percent;
}
