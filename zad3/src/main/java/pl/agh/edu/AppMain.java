package pl.agh.edu;

import javafx.application.Application;
import pl.agh.edu.visualizer.application.VisualizerBootstrap;

public class AppMain {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("USAGE: Watcher hostPort znode program [args ...]");
            System.exit(1);
        }

        Application.launch(VisualizerBootstrap.class, args);
    }
}
