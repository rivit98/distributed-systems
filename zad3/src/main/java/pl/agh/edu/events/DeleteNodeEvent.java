package pl.agh.edu.events;

import lombok.ToString;
import pl.agh.edu.visualizer.controllers.MainController;
import pl.agh.edu.visualizer.utils.Utils;
import pl.agh.edu.watcher.Message;

import java.nio.file.Path;
import java.util.stream.Collectors;

@ToString(callSuper = true)
public class DeleteNodeEvent extends AbstractEvent{
    public DeleteNodeEvent(Message payload) {
        super(payload);
    }

    @Override
    public void dispatch(MainController controller) {
        //TODO: main folder remove
        var nodeTree = controller.getNodeTree();

        var affectedNode = nodeTree.getPathToTreeMap().get(Path.of(payload.getPath()));
        if(affectedNode.equals(nodeTree.getRoot())){
            controller.removeMainNode();
        }

        nodeTree.removeMappedDirs(affectedNode);
        affectedNode.deleteMe();
    }
}
