package messages.satellite;

import lombok.Data;
import satellite.SatelliteAPI;

@Data
public class SatelliteResponse implements ISatelliteMessage {
    private final int queryID;
    private final int satelliteID;
    private final SatelliteAPI.Status status;
}
