package pl.agh.edu.watcher;

import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import pl.agh.edu.events.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class ZNodeWatcher implements Watcher {
    private final ZooKeeper zk;
    private final PublishSubject<IZnodeEvent> eventStream;

    public ZNodeWatcher(ZooKeeper zk, String znode, PublishSubject<IZnodeEvent> eventStream) throws InterruptedException, KeeperException {
        this.zk = zk;
        this.eventStream = eventStream;
        recursiveWatch(znode);
    }

    public void recursiveWatch(String znode) throws InterruptedException, KeeperException {
        if (zk.exists(znode, this) == null) { // register data change watch
            return;
        }

        var byteData = Optional
                .ofNullable(zk.getData(znode, false, null))
                .orElse(new byte[0]);

        var stringData = new String(byteData, StandardCharsets.UTF_8);
        eventStream.onNext(new CreateNodeEvent(new Message(znode, stringData)));

        var children = zk.getChildren(znode, this); // register children change watch
        for (String filename : children) {
            var znodeName = znode + "/" + filename;
            recursiveWatch(znodeName);
        }
    }

    public void process(WatchedEvent event) {
        try {
            var path = event.getPath();
            switch (event.getType()) {
                case NodeCreated -> handleNodeCreated(path);
                case NodeDeleted -> handleNodeDeleted(path);
                case NodeChildrenChanged -> handleChildrenChanged(path);
                case NodeDataChanged -> handleNodeDataChanged(path);
            }

            if(event.getType() == Event.EventType.None){
                handleNoneEvent(event);
            }else{
                zk.exists(path, this);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("processWatch", e);
        }
    }

    private void handleNodeDataChanged(String path) throws KeeperException, InterruptedException {
        var byteData = zk.getData(path, false, null);
        var stringData = new String(byteData, StandardCharsets.UTF_8);
        eventStream.onNext(new DataChangedEvent(new Message(path, stringData)));
    }

    private void handleNodeCreated(String path) throws KeeperException, InterruptedException {
        eventStream.onNext(new CreateNodeEvent(new Message(path)));

        zk.getChildren(path, this);
    }

    private void handleNodeDeleted(String path) {
        eventStream.onNext(new DeleteNodeEvent(new Message(path)));
    }

    private void handleChildrenChanged(String path) throws KeeperException, InterruptedException {
        if (zk.exists(path, false) == null) {
            return;
        }

        var children = zk.getChildren(path, this);
        eventStream.onNext(new ChildrenChangedEvent(new Message(path, children)));
        children.forEach(filename -> {
            try {
                var znodeName = path + "/" + filename;
                zk.exists(znodeName, this);
                zk.getChildren(znodeName, this);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleNoneEvent(WatchedEvent event){
        switch (event.getState()){
            case Disconnected -> log.warn("Watcher disconnected");
            case Expired -> log.warn("Watcher expired");
            case Closed -> log.warn("Watcher closed");
        }
    }
}