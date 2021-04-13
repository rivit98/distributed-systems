package pl.agh.edu.events;

import pl.agh.edu.visualizer.controllers.MainController;
import pl.agh.edu.watcher.Message;

import java.nio.file.Path;

public class DataChangedEvent extends AbstractEvent{
    public DataChangedEvent(Message payload) {
        super(payload);
    }

    @Override
    public void dispatch(MainController controller) {
        var nodeTree = controller.getNodeTree().getPathToTreeMap();

        var node = nodeTree.get(Path.of(payload.getPath()));
        node.getValue().setData(payload.getData());
    }
}
