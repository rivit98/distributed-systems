package pl.agh.edu.visualizer.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import pl.agh.edu.visualizer.MainController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class VisualizerBootstrap extends Application {
    private final String applicationTitle = "ZNodeVisualizer";
    private final String cssPath = "/styles/style.css";

    @Override
    public void init() {
        log.info("Starting JavaFX ZNodeVisualizer");
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            showMainView(primaryStage, getParameters().getRaw());
        } catch (IOException e) {
            log.warn("App start error", e);
        }
    }

    @Override
    public void stop() {
        log.info("Closing JavaFX ZNodeVisualizer");
        Platform.exit();
    }

    public void showMainView(Stage stage, List<String> args) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(getClass().getResource("/views/MainView.fxml"))
        );

        loader.setControllerFactory(controller -> new MainController(stage, args.toArray(new String[0])));

        Parent root = loader.load();
        MainController controller = loader.getController();

        var scene = new Scene(root);
        scene.getStylesheets().add(cssPath);

        stage.setResizable(false);
        stage.setTitle(applicationTitle);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> controller.onClose());
//        stage.show();
    }
}
