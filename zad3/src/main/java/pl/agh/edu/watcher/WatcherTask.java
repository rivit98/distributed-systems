package pl.agh.edu.watcher;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import pl.agh.edu.events.IZnodeEvent;
import pl.agh.edu.events.ZookeeperConnectedEvent;

import java.util.concurrent.Semaphore;

@Slf4j
public class WatcherTask extends Thread {
    private final String hostPort;
    private final String znode;
    private final Semaphore semaphore = new Semaphore(0);

    @Getter
    private final PublishSubject<IZnodeEvent> eventStream = PublishSubject.create();

    public WatcherTask(String hostPort, String znode) {
        super("WatcherTask");

        this.hostPort = hostPort;
        this.znode = znode;
    }

    @Override
    public void run() {
        log.info("Starting ZWatcher");

        try (ZooKeeper zk = new ZooKeeper(hostPort, 25000, null)) {
            eventStream.onNext(new ZookeeperConnectedEvent());

            var watcher = new ZNodeWatcher(zk, znode, eventStream);

            semaphore.acquire();
            log.info("ZWatcher exit");
        } catch (KeeperException.ConnectionLossException e) {
            log.error("ConnectionLost", e);
        } catch (Exception e) {
            log.error("WatcherTask", e);
        }
    }

    public void close() {
        log.info("ZWatcher close request");
        semaphore.release();
    }
}
