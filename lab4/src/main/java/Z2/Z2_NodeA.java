package Z2;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.Behaviors;
import akka.cluster.typed.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.net.URISyntaxException;


public class Z2_NodeA {

    public static Behavior<Void> create() {
        return Behaviors.setup(
                context -> {

                    context.spawn(ActorUpperCase.create(), "actor1");
                    context.spawn(ActorUpperCase.create(), "actor2");

                    return Behaviors.receive(Void.class)
                            .onSignal(Terminated.class, sig -> Behaviors.stopped())
                            .build();
                });
    }

    public static void main(String[] args) throws URISyntaxException {
        var className = Z2_NodeA.class.getClassLoader();
        var uri = className.getResource("Z2/nodeA.conf");

        File configFile = new File(uri.toURI());
        Config config = ConfigFactory.parseFile(configFile);
        System.out.println("Node A: config: " + config);

        ActorSystem.create(Z2_NodeA.create(), "ClusterSystem", config);
    }
}
