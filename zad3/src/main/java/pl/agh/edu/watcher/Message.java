package pl.agh.edu.watcher;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Message {
    private final String path;
    private final String data;
    private final List<String> nodesList;

    public Message(String path, String data, List<String> nodesList) {
        this.path = path;
        this.data = data;
        this.nodesList = nodesList;
    }

    public Message(String path, List<String> nodesList) {
        this(path, null, nodesList);
    }

    public Message(String path, String data) {
        this(path, data, null);
    }

    public Message(String path) {
        this(path, null, null);
    }
}
