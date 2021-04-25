package messages.db;

import lombok.Data;

import java.util.List;

@Data
public class DbUpdateStats implements IDbMessage {
    private final List<Integer> errorsIDs;
}
