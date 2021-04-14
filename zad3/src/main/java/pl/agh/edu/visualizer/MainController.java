package pl.agh.edu.visualizer;

import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.agh.edu.visualizer.bindings.DescendantsBinding;
import pl.agh.edu.visualizer.cellFactories.DataColumnCellFactory;
import pl.agh.edu.visualizer.cellFactories.PathColumnCellFactory;
import pl.agh.edu.visualizer.model.NodeData;
import pl.agh.edu.visualizer.model.NodeTree;
import pl.agh.edu.watcher.WatcherTask;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

@Slf4j
@Getter
public class MainController {
    private final Stage myStage;
    private final NodeTree nodeTree = new NodeTree();
    private final WatcherTask watcherTask;
    private final String[] execArgs;

    @FXML private TreeTableView<NodeData> treeTableView;
    @FXML private TreeTableColumn<NodeData, Path> pathColumn;
    @FXML private TreeTableColumn<NodeData, String> dataColumn;
    @FXML private Text descendantsNum;
    private Process process;

    public MainController(Stage stage, String[] args) {
        this.myStage = stage;
        this.watcherTask = new WatcherTask(args[0], args[1]);
        this.execArgs = new String[args.length - 2];
        System.arraycopy(args, 2, this.execArgs, 0, this.execArgs.length);
    }

    @FXML
    public void initialize() {
        prepareColumns();

        watchEvents();
        watcherTask.start();
    }

    private void prepareColumns() {
        treeTableView.setRoot(new TreeItem<>());
        treeTableView.getRoot().setExpanded(true);

        pathColumn.setCellFactory(ttc -> new PathColumnCellFactory());
        pathColumn.setCellValueFactory(node -> node.getValue().getValue().getPathProperty());

        dataColumn.setCellFactory(ttc -> new DataColumnCellFactory());
        dataColumn.setCellValueFactory(node -> node.getValue().getValue().getDataProperty());

        descendantsNum.textProperty()
                .bind(new DescendantsBinding(nodeTree.getPathToTreeMap()).asString());
    }

    private void watchEvents() {
        watcherTask
                .getEventStream()
                .observeOn(JavaFxScheduler.platform())
                .doOnNext(e -> log.info(e.toString()))
                .doOnError(t -> log.error(t.toString()))
                .subscribe(event -> event.dispatch(this));
    }

    public void removeRoot() {
        nodeTree.clear();
        treeTableView.getRoot().getChildren().clear();

        stopProcess();
    }

    public void addRoot() {
        log.info("addRoot " + nodeTree.getRoot().getValue().getPath());
        treeTableView.getRoot().getChildren().clear();
        treeTableView.getRoot().getChildren().add(nodeTree.getRoot());

        startProcess();
    }

    public void expandTreeView(TreeItem<?> item) {
        if (item != null && !item.isLeaf()) {
            item.setExpanded(true);
            for (var child : item.getChildren()) {
                expandTreeView(child);
            }
        }
    }

    public void stopProcess() {
        if (process == null) {
            return;
        }
        log.info("Stopping process | PID: " + process.pid());

        process.destroy();
        process = null;
    }

    public void startProcess() {
        stopProcess();

        log.info("Starting process");
        try {
            this.process = new ProcessBuilder(execArgs).start();
        } catch (IOException e) {
            log.error("Cannot run process " + Arrays.toString(execArgs), e);
        }
    }

    public void onClose() {
        stopProcess();
        watcherTask.close();
    }
}
