package pl.agh.edu.events;

import lombok.ToString;
import pl.agh.edu.visualizer.MainController;
import pl.agh.edu.watcher.Message;

import java.nio.file.Path;

@ToString(callSuper = true)
public class DeleteNodeEvent extends AbstractEvent {
    public DeleteNodeEvent(Message payload) {
        super(payload);
    }

    @Override
    public void dispatch(MainController controller) {
        var nodeTree = controller.getNodeTree();

        var affectedNode = nodeTree.getPathToTreeMap().get(Path.of(payload.getPath()));
        if (affectedNode.equals(nodeTree.getRoot())) {
            controller.removeRoot();
        } else {
            nodeTree.removeMappedDirs(affectedNode);
            affectedNode.deleteMe();
        }
    }
}
