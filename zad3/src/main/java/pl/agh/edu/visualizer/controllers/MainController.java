package pl.agh.edu.visualizer.controllers;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.agh.edu.visualizer.controllers.cellFactories.DataColumnCellFactory;
import pl.agh.edu.visualizer.controllers.cellFactories.PathColumnCellFactory;
import pl.agh.edu.visualizer.model.NodeData;
import pl.agh.edu.visualizer.model.NodeTree;
import pl.agh.edu.watcher.WatcherTask;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@Getter
public class MainController {

    @FXML private TreeTableView<NodeData> treeTableView;
    @FXML private TreeTableColumn<NodeData, Path> pathColumn;
    @FXML private TreeTableColumn<NodeData, String> dataColumn;

    @Setter private String[] args;
    @Setter private Stage myStage;

    private final NodeTree nodeTree = new NodeTree();
    private WatcherTask receiverThread;
    private Disposable eventDisposable;

    @FXML
    public void initialize() {
        receiverThread = new WatcherTask(args);
        createRoot();
        prepareColumns();

        watchEvents();
        receiverThread.start();
    }

    private void createRoot() {
        treeTableView.setRoot(new TreeItem<>());
        treeTableView.getRoot().setExpanded(true);
    }

    private void prepareColumns() {
        pathColumn.setCellFactory(ttc -> new PathColumnCellFactory());
        pathColumn.setCellValueFactory(node -> node.getValue().getValue().getPathProperty());

        dataColumn.setCellFactory(ttc -> new DataColumnCellFactory());
        dataColumn.setCellValueFactory(node -> node.getValue().getValue().getDataProperty());

//        treeTableView.sortPolicyProperty().set(new MainControllerSortPolicy()); //TODO: sorting
    }


//    public boolean removeFolder(ILimitableObservableFolder folder, TreeItem<NodeData> treeItem) {
//        if (isMainFolder(treeItem)) {
//            return removeMainFolder(folder, treeItem);
//        } else {
//            return ((TreeFileNode) treeItem).deleteMe();
//        }
//    }
//
//    public boolean removeTreeItem(TreeItem<NodeData> treeItem) {
//        return folderList.getObservedFolderFromTreeItem(treeItem)
//                .map(folder -> removeFolder(folder, treeItem))
//                .orElse(false);
//    }
//
//    public boolean isMainFolder(TreeItem<NodeData> node) {
//        return treeTableView.getRoot().getChildren().contains(node);
//    }
//
//    public boolean removeMainFolder(TreeItem<NodeData> nodeToRemove) {
//        treeTableView.getRoot().getChildren().remove(nodeToRemove);
//        if (treeTableView.getRoot().getChildren().isEmpty()) {
//            treeTableView.getSelectionModel().clearSelection();
//        }
//    }

    private void watchEvents() {
        eventDisposable = receiverThread
                .getEventStream()
                .observeOn(JavaFxScheduler.platform())
                .doOnNext(System.out::println)
                .subscribe(
                        event -> event.dispatch(this),
                        System.out::println
                );
    }

    public void expandTreeView(TreeItem<?> item){
        if(item != null && !item.isLeaf()){
            item.setExpanded(true);
            for(TreeItem<?> child:item.getChildren()){
                expandTreeView(child);
            }
        }
    }

    public void onClose() {
        eventDisposable.dispose();
        receiverThread.close();
    }

    public void removeMainNode() {

    }

    public void addRoot() {
        treeTableView.getRoot().getChildren().clear();
        treeTableView.getRoot().getChildren().add(nodeTree.getRoot());
    }
}
