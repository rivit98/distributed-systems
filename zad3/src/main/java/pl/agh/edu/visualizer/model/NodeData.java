package pl.agh.edu.visualizer.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;

@Getter
public class NodeData implements Comparable<NodeData> {
    private final SimpleObjectProperty<Path> pathProperty = new SimpleObjectProperty<>();
    private final SimpleStringProperty dataProperty = new SimpleStringProperty();

    public NodeData(String path, String data) {
        this.pathProperty.set(Path.of(path));
        setData(data);
    }

    public void setData(String data){
        dataProperty.set(data);
    }

    public Path getPath() {
        return pathProperty.get();
    }

    public String getData() {
        return dataProperty.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var nodeData = (NodeData) o;
        return Objects.equals(getPath(), nodeData.getPath());
    }

    @Override
    public int compareTo(NodeData other) {
        return Comparator
                .comparing(NodeData::getPath)
                .thenComparing(NodeData::getData)
                .compare(this, other);
    }
}
