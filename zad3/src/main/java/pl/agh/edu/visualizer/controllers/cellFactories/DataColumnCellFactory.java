package pl.agh.edu.visualizer.controllers.cellFactories;

import javafx.scene.control.TreeTableCell;
import pl.agh.edu.visualizer.model.NodeData;

public class DataColumnCellFactory extends TreeTableCell<NodeData, String> {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
            return;
        }

        setText(item);
    }
}
