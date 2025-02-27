package Z1;

import akka.actor.typed.ActorSystem;

public class Z1_Main {
    public static void main(String[] args) throws Exception {
        // create actor system
        var system = ActorSystem.create(MathActor.create(), "actorMath");

        // send messages
        system.tell(new MathActor.MathCommandAdd(5, 3));
        system.tell(new MathActor.MathCommandMultiply(5, 3, null));
        system.tell(new MathActor.MathCommandMultiply(5, 2, null));
        system.tell(new MathActor.MathCommandDivide(15, 3, null));
        system.tell(new MathActor.MathCommandDivide(15, 5, null));

        system.tell(new MathActor.MathCommandDivide(15, 0, null));
        Thread.sleep(2000);

        system.tell(new MathActor.MathCommandMultiply(5, 3, null));
        system.tell(new MathActor.MathCommandMultiply(5, 2, null));
        system.tell(new MathActor.MathCommandDivide(15, 3, null));
        system.tell(new MathActor.MathCommandDivide(15, 5, null));
    }
}
