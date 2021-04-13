package pl.agh.edu.watcher;

import io.reactivex.rxjava3.subjects.PublishSubject;
import javafx.beans.Observable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import pl.agh.edu.events.IZnodeEvent;
import pl.agh.edu.events.ZookeeperConnectedEvent;
import pl.agh.edu.visualizer.AppMain;

import java.io.IOException;

@Slf4j
public class WatcherTask extends Thread{
    private final String hostPort;
    private final String znode;

    @Getter
    private final PublishSubject<IZnodeEvent> eventStream = PublishSubject.create();

    public WatcherTask(String[] args) {
        super("WatcherTask");

        args = new String[]{"127.0.0.1:2182", "/z", "calc.exe"};

        hostPort = args[0];
        znode = args[1];
        var exec = new String[args.length - 2];
        System.arraycopy(args, 2, exec, 0, exec.length);
    }

    @Override
    public void run() {
        try {
            log.info("Starting ZWatcher");
            ZooKeeper zk = new ZooKeeper(hostPort, 3000, null);
            eventStream.onNext(new ZookeeperConnectedEvent());

            var watcher = new ZNodeWatcher(zk, znode, eventStream);

            synchronized (this){
                wait(); //TODO verify this
            }

            log.info("ZWatcher exit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        log.info("ZWatcher close request");
        synchronized (this){
            notifyAll();
        }
    }
}
