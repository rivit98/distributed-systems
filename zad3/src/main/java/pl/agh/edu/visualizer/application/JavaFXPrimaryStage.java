package pl.agh.edu.visualizer.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.agh.edu.visualizer.controllers.MainController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

class JavaFXPrimaryStage {
    private final String applicationTitle;
    private final String cssPath = "/styles/style.css";


    public JavaFXPrimaryStage(String title) {
        this.applicationTitle = title;
    }

    public void showMainView(Stage stage, List<String> args) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(getClass().getResource("/views/MainView.fxml"))
        );

        Parent root = loader.load();
        MainController controller = loader.getController();

        controller.setArgs(args.toArray(new String[0]));
        controller.setMyStage(stage);

        var scene = new Scene(root);
        scene.getStylesheets().add(cssPath);

        stage.setResizable(false);
        stage.setTitle(applicationTitle);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> controller.onClose());
//        stage.show();
    }
}
