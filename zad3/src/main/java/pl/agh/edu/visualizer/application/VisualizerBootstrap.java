package pl.agh.edu.visualizer.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class VisualizerBootstrap extends Application {
    private JavaFXPrimaryStage javaFXPrimaryStage;

    @Override
    public void init() {
        log.info("Starting JavaFX ZNodeVisualizer");
        javaFXPrimaryStage = new JavaFXPrimaryStage("ZNodeVisualizer");
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            javaFXPrimaryStage.showMainView(primaryStage, getParameters().getRaw());
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void stop() {
        log.info("Closing JavaFX ZNodeVisualizer");
        Platform.exit();
    }
}
