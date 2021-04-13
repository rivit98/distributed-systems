package pl.agh.edu.watcher;

import java.util.List;

public interface IZNodeListener {

    void onCreate(String path, String data);

    void onDelete(String path);

    void onDataChanged(String path, String data);

    void onChildrenChanged(String path, List<String> children);
}
