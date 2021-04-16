package Z2;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.receptionist.Receptionist;

import java.util.LinkedList;
import java.util.List;

public class ActorTextService extends AbstractBehavior<ActorTextService.Command> {
    interface Command {
    }

    public static class ListingResponse implements Command {
        final Receptionist.Listing listing;

        public ListingResponse(Receptionist.Listing listing) {
            this.listing = listing;
        }
    }

    public static class Request implements Command {
        final String text;

        public Request(String text) {
            this.text = text;
        }
    }

    private final ActorRef<Receptionist.Listing> adapter;
    private final List<ActorRef<String>> workers = new LinkedList<>();

    public ActorTextService(ActorContext<ActorTextService.Command> context) {
        super(context);

        this.adapter = context.messageAdapter(Receptionist.Listing.class, ListingResponse::new);

        context.getSystem().receptionist().tell(Receptionist.subscribe(ActorUpperCase.upperCaseServiceKey, adapter));
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(ActorTextService::new);
    }

    @Override
    public Receive<Command> createReceive() {

        System.out.println("creating receive for text service");

        return newReceiveBuilder()
                .onMessage(Request.class, this::onRequest)
                .onMessage(ListingResponse.class, this::onListingResponse)
                .build();
    }

    private Behavior<Command> onRequest(Request msg) {
        System.out.println("request: " + msg.text);
        for (ActorRef<String> worker : workers) {
            System.out.println("sending to worker: " + worker);
            worker.tell(msg.text);
        }
        return this;
    }

    private Behavior<Command> onListingResponse(ListingResponse msg) {
//        workers.clear();
        workers.addAll(msg.listing.getAllServiceInstances(ActorUpperCase.upperCaseServiceKey));
        return this;
    }
}
