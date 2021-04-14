package pl.agh.edu.visualizer.model;

import javafx.scene.control.TreeItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Getter
public class NodeTree {
    private final Map<Path, TreeFileNode> pathToTreeMap = new HashMap<>();
    private TreeFileNode root;

    public void clear() {
        log.info("Clearing nodeMap");
        pathToTreeMap.clear();
        root = null;
    }

    public boolean addNode(Path path) {
        return addNode(path, "");
    }

    public boolean addNode(Path path, String data) {
        var nodeData = new NodeData(path, data);
        var insertedNode = new TreeFileNode(nodeData);

        if (root != null) {
            insertNewNode(insertedNode);
        } else {
            pathToTreeMap.put(nodeData.getPath(), insertedNode);
            root = insertedNode;
        }

        return root != null;
    }

    public void insertNewNode(TreeFileNode newNode) {
        var nodeData = newNode.getValue();
        var parentPath = nodeData.getPath().getParent();
        var parentNode = pathToTreeMap.get(parentPath);
        parentNode.insertNode(newNode);
        pathToTreeMap.put(nodeData.getPath(), newNode);
    }

    public void removeMappedDirs(TreeItem<NodeData> node) {
        removeMappedDirsSingle(node);
        node.getChildren().forEach(this::removeMappedDirs);
    }

    private void removeMappedDirsSingle(TreeItem<NodeData> node) {
        Optional.ofNullable(node)
                .map(TreeItem::getValue)
                .map(NodeData::getPath)
                .ifPresent(pathToTreeMap::remove);
    }
}

