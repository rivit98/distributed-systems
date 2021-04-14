package pl.agh.edu.visualizer.bindings;

import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import pl.agh.edu.visualizer.model.TreeFileNode;

import java.nio.file.Path;

public class DescendantsBinding extends IntegerBinding {
    private final ObservableMap<Path, TreeFileNode> op;

    public DescendantsBinding(ObservableMap<Path, TreeFileNode> map) {
        super.bind(map);
        this.op = map;
    }

    @Override
    public void dispose() {
        super.unbind(op);
    }

    @Override
    protected int computeValue() {
        return Math.max(0, op.size()-1);
    }

    @Override
    public ObservableList<?> getDependencies() {
        return FXCollections.singletonObservableList(op);
    }
}
