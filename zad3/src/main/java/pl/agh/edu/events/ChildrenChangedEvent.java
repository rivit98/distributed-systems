package pl.agh.edu.events;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pl.agh.edu.visualizer.MainController;
import pl.agh.edu.watcher.Message;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ToString(callSuper = true)
public class ChildrenChangedEvent extends AbstractEvent {
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

        var newNodes = listOfStringsToListOfPaths();

        var diff = diffList(currentChildren, newNodes);
        if (diff.size() == 1) {
            var newPath = diff.get(0);
            nodeTree.addNode(newPath);
            controller.expandTreeView(nodeMap.get(newPath).getParent());
        }
    }

    private List<Path> listOfStringsToListOfPaths() {
        var parent = Path.of(payload.getPath());
        return payload.getNodesList()
                .stream()
                .map(s -> Path.of(parent + "/" + s))
                .collect(Collectors.toList());
    }

    private List<Path> diffList(List<Path> oldNodes, List<Path> newNodes) {
        Set<Path> oldNodesSet = new HashSet<>(oldNodes);
        Set<Path> newNodesSet = new HashSet<>(newNodes);
        newNodesSet.removeAll(oldNodesSet);
        return new ArrayList<>(newNodesSet);
    }
}
