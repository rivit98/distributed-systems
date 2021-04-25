package db;

import akka.actor.typed.Behavior;
import akka.actor.typed.Signal;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import messages.DbUpdateStats;
import messages.ErrorStatsQuery;
import messages.ErrorStatsResponse;
import messages.IMessage;

public class DatabaseActor extends AbstractBehavior<IMessage> {
    private final SQLiteService db = new SQLiteService();

    public DatabaseActor(ActorContext<IMessage> context) {
        super(context);
    }

    public static Behavior<IMessage> create() {
        return Behaviors.setup(DatabaseActor::new);
    }


    @Override
    public Receive<IMessage> createReceive() {
        return newReceiveBuilder()
                .onSignal(Terminated.class, this::onTerminated)
                .onMessage(DbUpdateStats.class, this::onUpdateStats)
                .onMessage(ErrorStatsQuery.class, this::onErrorStatsQuery)
                .build();
    }

    private Behavior<IMessage> onErrorStatsQuery(ErrorStatsQuery query) {
        var satID = query.getSatelliteID();
        var errorsNum = db.getErrorCount(satID);

        query.getReplyTo().tell(new ErrorStatsResponse(satID, errorsNum));
        return this;
    }

    private Behavior<IMessage> onUpdateStats(DbUpdateStats stats) {
        db.updateMany(stats.getErrorsIDs());
        return this;
    }

    private Behavior<IMessage> onTerminated(Signal s) {
        db.close();
        return Behaviors.stopped();
    }
}
