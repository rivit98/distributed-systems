package pl.agh.edu.events;

import pl.agh.edu.visualizer.controllers.MainController;

public class ZookeeperConnectedEvent implements IZnodeEvent{

    @Override
    public void dispatch(MainController controller) {
        controller.getMyStage().show();
    }
}
