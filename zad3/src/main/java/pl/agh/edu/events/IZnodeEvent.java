package pl.agh.edu.events;

import pl.agh.edu.visualizer.MainController;

public interface IZnodeEvent {
    void dispatch(MainController controller);
}
