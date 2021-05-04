package services.rest;

import java.util.HashMap;
import java.util.Map;

public class TodoDao {
    private static final Map<String, Todo> contentProvider = new HashMap<String, Todo>() {{
        put("1", new Todo("1", "Learn REST theory", "Attend the REST lecture on the TAI course"));
        put("2", new Todo("2", "Learn REST practice", "Attend the REST laboratory on the TAI course"));
    }};

    public static Map<String, Todo> getModel(){
        return contentProvider;
    }
}