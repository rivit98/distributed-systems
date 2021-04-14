package pl.agh.edu.visualizer.cellFactories;

import javafx.scene.control.TreeTableCell;
import pl.agh.edu.visualizer.model.NodeData;

import java.nio.file.Path;

public class PathColumnCellFactory extends TreeTableCell<NodeData, Path> {
    @Override
    protected void updateItem(Path item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
            return;
        }

        setText(translateSlashes(item.toString()));
//        setText(translateSlashes(item.getFileName().toString()));
    }

    private String translateSlashes(String from) {
        return from.replace("\\", "/");
    }

}
