package pl.agh.edu.watcher;

import io.reactivex.rxjava3.subjects.PublishSubject;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import pl.agh.edu.events.*;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class ZNodeWatcher implements Watcher {
    private final ZooKeeper zk;
    private final PublishSubject<IZnodeEvent> eventStream;


    public ZNodeWatcher(ZooKeeper zk, String znode, PublishSubject<IZnodeEvent> eventStream) throws InterruptedException, KeeperException {
        this.zk = zk;
        this.eventStream = eventStream;
        recursiveWatch(znode);
    }

    public void recursiveWatch(String znode) throws InterruptedException, KeeperException {
        var nodeExists = zk.exists(znode, this) != null; // register data change watch

        if (!nodeExists) {
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
                case NodeCreated -> {
                    eventStream.onNext(new CreateNodeEvent(new Message(path)));

                    zk.getChildren(path, this);
                }

                case NodeDeleted -> eventStream.onNext(new DeleteNodeEvent(new Message(path)));

                case NodeChildrenChanged -> {
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

                case NodeDataChanged -> {
                    var byteData = zk.getData(path, false, null);
                    var stringData = new String(byteData, StandardCharsets.UTF_8);
                    eventStream.onNext(new DataChangedEvent(new Message(path, stringData)));
                }
            }

            zk.exists(path, this);

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}