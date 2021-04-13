package pl.agh.edu.events;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.agh.edu.watcher.Message;

@AllArgsConstructor
@ToString
public abstract class AbstractEvent implements IZnodeEvent {
    protected final Message payload;
}
