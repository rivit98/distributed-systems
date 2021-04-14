package pl.agh.edu.events;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.agh.edu.watcher.Message;

@AllArgsConstructor
public abstract class AbstractEvent implements IZnodeEvent {
    protected final Message payload;

    @Override
    public String toString() {
        return payload.toString();
    }
}
