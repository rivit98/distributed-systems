package pl.agh.edu.events;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pl.agh.edu.visualizer.controllers.MainController;
import pl.agh.edu.visualizer.utils.Utils;
import pl.agh.edu.watcher.Message;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ToString(callSuper = true)
public class ChildrenChangedEvent extends AbstractEvent{
    public ChildrenChangedEvent(Message payload) {
        super(payload);
    }

    @Override
    public void dispatch(MainController controller) {
        var nodeTree = controller.getNodeTree();
        var nodeMap = nodeTree.getPathToTreeMap();
        var parent = Path.of(payload.getPath());

        var currentChildren = nodeMap.get(parent)
                .getChildren()
                .stream()
                .map(n -> n.getValue().getPath())
                .collect(Collectors.toList());

        var newNodes = payload.getNodesList()
                .stream()
                .map(s -> Path.of(parent + "/" + s))
                .collect(Collectors.toList());

        var diff = diffList(currentChildren, newNodes);
        var diffSize = diff.size();
        if(diffSize == 1){
            var newPath = diff.get(0);
            nodeTree.addNode(newPath.toString());
        }
    }

    private List<Path> diffList(List<Path> oldNodes, List<Path> newNodes){
        Set<Path> oldNodesSet = new HashSet<>(oldNodes);
        Set<Path> newNodesSet = new HashSet<>(newNodes);
        newNodesSet.removeAll(oldNodesSet);
        return new ArrayList<>(newNodesSet);
    }
}
