package Z1;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;


public class MathActor extends AbstractBehavior<MathActor.MathCommand> {

    public interface MathCommand {}

    public static final class MathCommandAdd implements MathCommand {
        public final int firstNumber;
        public final int secondNumber;

        public MathCommandAdd(int firstNumber, int secondNumber) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
        }
    }

    public static final class MathCommandMultiply implements MathCommand {
        public final int firstNumber;
        public final int secondNumber;
        public final ActorRef<MathCommand> replyTo;

        public MathCommandMultiply(int firstNumber, int secondNumber, ActorRef<MathActor.MathCommand> replyTo) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
            this.replyTo = replyTo;
        }
    }

    public static final class MathCommandDivide implements MathCommand {
        public final int firstNumber;
        public final int secondNumber;
        public final ActorRef<MathCommand> replyTo;

        public MathCommandDivide(int firstNumber, int secondNumber, ActorRef<MathActor.MathCommand> replyTo) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
            this.replyTo = replyTo;
        }
    }

    public static final class MathCommandResult implements MathCommand {
        public final int result;

        public MathCommandResult(int result) {
            this.result = result;
        }
    }

    private final ActorRef<MathActor.MathCommandMultiply> actorMultiply;
    private final ActorRef<MathActor.MathCommandDivide> actorDivide;

    public MathActor(ActorContext<MathCommand> context) {
        super(context);
        actorMultiply = getContext().spawn(MathActorMultiply.create(), "actorMultiply");
        actorDivide = getContext().spawn(
                Behaviors.supervise(MathActorDivide.create())
                        .onFailure(Exception.class, SupervisorStrategy.restart()), "actorDivide");
    }

    public static Behavior<MathActor.MathCommand> create() {
        return Behaviors.setup(MathActor::new);
    }

    @Override
    public Receive<MathCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(MathCommandAdd.class, this::onMathCommandAdd)
                .onMessage(MathCommandMultiply.class, this::onMathCommandMultiply)
                .onMessage(MathCommandDivide.class, this::onMathCommandDivide)
                .onMessage(MathCommandResult.class, this::onMathCommandResult)
                .build();
    }

    private Behavior<MathCommand> onMathCommandAdd(MathCommandAdd mathCommandAdd) {
        int result = mathCommandAdd.firstNumber + mathCommandAdd.secondNumber;
        return this;
    }

    private Behavior<MathCommand> onMathCommandMultiply(MathCommandMultiply mathCommandMultiply) {
        actorMultiply.tell(new MathActor.MathCommandMultiply(mathCommandMultiply.firstNumber, mathCommandMultiply.secondNumber, getContext().getSelf()));
        return this;
    }

    private Behavior<MathCommand> onMathCommandDivide(MathCommandDivide mathCommandDivide) {
        actorDivide.tell(new MathActor.MathCommandDivide(mathCommandDivide.firstNumber, mathCommandDivide.secondNumber, getContext().getSelf()));
        return this;
    }

    private Behavior<MathCommand> onMathCommandResult(MathCommandResult mathCommandResult) {
//        System.out.println("actorMath: received result: " + mathCommandResult.result);
        return this;
    }
}
