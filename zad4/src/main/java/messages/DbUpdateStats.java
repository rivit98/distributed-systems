package messages;

import lombok.Data;

import java.util.List;

@Data
public class DbUpdateStats implements IMessage {
    private final List<Integer> errorsIDs;
}
