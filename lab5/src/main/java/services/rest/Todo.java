package services.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Todo {
    private String id;
    private String summary;
    private String description;

    public Todo(String id, String summary) {
        this.id = id;
        this.summary = summary;
    }
}
