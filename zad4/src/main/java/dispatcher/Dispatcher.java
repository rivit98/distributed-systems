package dispatcher;

import akka.actor.typed.Behavior;
import akka.actor.typed.DispatcherSelector;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.extern.slf4j.Slf4j;
import messages.IMessage;
import messages.StationQuery;
import satellite.SatellitesSupervisor;

@Slf4j
public class Dispatcher extends AbstractBehavior<IMessage> {
    private int supervisorID = 0;

    public Dispatcher(ActorContext<IMessage> context) {
        super(context);
    }

    public static Behavior<IMessage> create() {
        return Behaviors.setup(Dispatcher::new);
    }

    @Override
    public Receive<IMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(StationQuery.class, this::onDispatcherQueryMessage)
                .build();
    }

    private Behavior<IMessage> onDispatcherQueryMessage(StationQuery query) {
        getContext()
                .spawn(
                        SatellitesSupervisor.create(),
                        "satellite-supervisor" + supervisorID,
                        DispatcherSelector.fromConfig("my-dispatcher")
                )
                .tell(query);

        supervisorID++;
        return this;
    }
}
