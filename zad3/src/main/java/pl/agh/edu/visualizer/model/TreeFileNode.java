package pl.agh.edu.visualizer.model;

import javafx.scene.control.TreeItem;

import java.util.Objects;
import java.util.Optional;


public class TreeFileNode extends TreeItem<NodeData> {
    public TreeFileNode(NodeData nodeData) {
        super(nodeData);
    }

    public void insertNode(TreeFileNode node) {
        var currentValue = node.getValue();
        var cachedList = getChildren();
        var index = cachedList.stream().takeWhile(childNode -> currentValue.compareTo(childNode.getValue()) > 0).count();

        cachedList.add((int) index, node);
    }

    public boolean deleteMe() {
        return Optional.ofNullable(this.getParent())
                .map(TreeItem::getChildren)
                .map(childrenList -> childrenList.remove(this))
                .orElse(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var node = (TreeFileNode) o;
        return Objects.equals(getValue(), node.getValue());
    }
}
