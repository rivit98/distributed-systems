package db;

import akka.actor.typed.Behavior;
import akka.actor.typed.Signal;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import messages.ErrorStatsResponse;
import messages.db.DbUpdateStats;
import messages.db.ErrorStatsQuery;
import messages.db.IDbMessage;

public class DatabaseActor extends AbstractBehavior<IDbMessage> {
    private final SQLiteService db = new SQLiteService();

    public DatabaseActor(ActorContext<IDbMessage> context) {
        super(context);
    }

    public static Behavior<IDbMessage> create() {
        return Behaviors.supervise(Behaviors.setup(DatabaseActor::new))
                .onFailure(Exception.class, SupervisorStrategy.restart());
    }

    @Override
    public Receive<IDbMessage> createReceive() {
        return newReceiveBuilder()
                .onSignal(Terminated.class, this::onTerminated)
                .onMessage(DbUpdateStats.class, this::onUpdateStats)
                .onMessage(ErrorStatsQuery.class, this::onErrorStatsQuery)
                .build();
    }

    private Behavior<IDbMessage> onErrorStatsQuery(ErrorStatsQuery query) {
        var satID = query.getSatelliteID();
        var errorsNum = db.getErrorCount(satID);

        query.getReplyTo().tell(new ErrorStatsResponse(satID, errorsNum));
        return this;
    }

    private Behavior<IDbMessage> onUpdateStats(DbUpdateStats stats) {
        db.updateMany(stats.getErrorsIDs());
        return this;
    }

    private Behavior<IDbMessage> onTerminated(Signal s) {
        db.close();
        return Behaviors.stopped();
    }
}
