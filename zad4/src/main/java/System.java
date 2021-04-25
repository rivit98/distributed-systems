import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.DispatcherSelector;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.typesafe.config.ConfigFactory;
import db.DatabaseActor;
import dispatcher.Dispatcher;
import lombok.extern.slf4j.Slf4j;
import messages.ErrorStatsQuery;
import messages.IMessage;
import messages.StationQuery;
import station.Station;

import java.io.File;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class System extends AbstractBehavior<IMessage> {
    public System(ActorContext<IMessage> context) throws InterruptedException {
        super(context);

        var props = DispatcherSelector.fromConfig("my-dispatcher");
        var dispatcher = context.spawn(Dispatcher.create(), "dispatcher", props);
        var database = context.spawn(DatabaseActor.create(), "db", props); //TODO: resume strategy

        var station1 = context.spawn(Station.create(dispatcher, database), "station1", props);
        var station2 = context.spawn(Station.create(dispatcher, database), "station2", props);
        var station3 = context.spawn(Station.create(dispatcher, database), "station3", props);

        var random = new Random();
        station1.tell(new StationQuery(100 + random.nextInt(50), 50, Duration.ofMillis(300)));
        station1.tell(new StationQuery(100 + random.nextInt(50), 50, Duration.ofMillis(300)));
        station2.tell(new StationQuery(100 + random.nextInt(50), 50, Duration.ofMillis(300)));
        station2.tell(new StationQuery(100 + random.nextInt(50), 50, Duration.ofMillis(300)));
        station3.tell(new StationQuery(100 + random.nextInt(50), 50, Duration.ofMillis(300)));
        station3.tell(new StationQuery(100 + random.nextInt(50), 50, Duration.ofMillis(300)));

        Thread.sleep(1000);

        IntStream.range(100, 200).forEach(id -> station1.tell(new ErrorStatsQuery(id)));
    }

    public static Behavior<IMessage> create() {
        return Behaviors.setup(System::new);
    }

    @Override
    public Receive<IMessage> createReceive() {
        return newReceiveBuilder()
                .onSignal(Terminated.class, signal -> Behaviors.stopped())
                .build();
    }

    public static void main(String[] args) throws URISyntaxException {
        var className = System.class.getClassLoader();
        var uri = className.getResource("dispatcher.conf");
        var configFile = new File(Objects.requireNonNull(uri).toURI());
        var config = ConfigFactory.parseFile(configFile);

        ActorSystem.create(System.create(), "astra-link", config);
    }
}
