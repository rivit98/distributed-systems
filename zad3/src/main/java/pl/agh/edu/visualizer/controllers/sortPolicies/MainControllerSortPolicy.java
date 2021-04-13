package pl.agh.edu.visualizer.controllers.sortPolicies;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;
import pl.agh.edu.visualizer.model.NodeData;

import java.util.Comparator;

public class MainControllerSortPolicy implements Callback<TreeTableView<NodeData>, Boolean> {

    private static final Comparator<TreeItem<NodeData>> comparator =
            Comparator.comparing(
                    (TreeItem<NodeData> treeItem) -> treeItem
                            .getValue()
                            .getPath()
                            .toString()
            );


    @Override
    public Boolean call(TreeTableView<NodeData> param) {
        param.getRoot().getChildren().sort(comparator);
        return true;
    }
}
