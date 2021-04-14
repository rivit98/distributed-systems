package pl.agh.edu.watcher;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import pl.agh.edu.events.IZnodeEvent;
import pl.agh.edu.events.ZookeeperConnectedEvent;

@Slf4j
public class WatcherTask extends Thread {
    private final String hostPort;
    private final String znode;

    @Getter
    private final PublishSubject<IZnodeEvent> eventStream = PublishSubject.create();

    public WatcherTask(String hostPort, String znode) {
        super("WatcherTask");

        this.hostPort = hostPort;
        this.znode = znode;
    }

    @Override
    public void run() {
        try {
            log.info("Starting ZWatcher");
            ZooKeeper zk = new ZooKeeper(hostPort, 3000, null);
            eventStream.onNext(new ZookeeperConnectedEvent());

            var watcher = new ZNodeWatcher(zk, znode, eventStream);

            synchronized (this) {
                wait(); //TODO verify this
            }

            log.info("ZWatcher exit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        log.info("ZWatcher close request");
        synchronized (this) {
            notifyAll();
        }
    }
}
