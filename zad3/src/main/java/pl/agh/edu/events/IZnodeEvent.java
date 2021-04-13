package pl.agh.edu.events;

import pl.agh.edu.visualizer.controllers.MainController;

public interface IZnodeEvent {
    void dispatch(MainController controller);
}
