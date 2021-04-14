package pl.agh.edu.events;

import lombok.ToString;
import pl.agh.edu.visualizer.MainController;
import pl.agh.edu.watcher.Message;

import java.nio.file.Path;

@ToString(callSuper = true)
public class CreateNodeEvent extends AbstractEvent {
    public CreateNodeEvent(Message payload) {
        super(payload);
    }

    @Override
    public void dispatch(MainController controller) {
        var nodeTree = controller.getNodeTree();

        var wasRoot = nodeTree.addNode(Path.of(payload.getPath()), payload.getData());
        if (wasRoot) {
            controller.addRoot();
        }

        controller.expandTreeView(nodeTree.getRoot());
    }
}
