package Z2;

import akka.actor.Actor;
import akka.actor.typed.*;
import akka.actor.typed.javadsl.Behaviors;
import akka.cluster.typed.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.net.URISyntaxException;


public class Z2_NodeB {

    public static Behavior<Void> create() {
        return Behaviors.setup(
                context -> {

                    var actorTextService = context.spawn(ActorTextService.create(), "textService");
                    Thread.sleep(10000);
                    actorTextService.tell(new ActorTextService.Request("hello"));

                    return Behaviors.receive(Void.class)
                            .onSignal(Terminated.class, sig -> Behaviors.stopped())
                            .build();
                });
    }

    public static void main(String[] args) throws URISyntaxException {
        var className = Z2_NodeA.class.getClassLoader();
        var uri = className.getResource("Z2/nodeB.conf");

        File configFile = new File(uri.toURI());
        Config config = ConfigFactory.parseFile(configFile);
        System.out.println("Node B: config: " + config);

        ActorSystem.create(Z2_NodeB.create(), "ClusterSystem", config);
    }
}
