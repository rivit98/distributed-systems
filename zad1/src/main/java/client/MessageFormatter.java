package client;

import lombok.AllArgsConstructor;
import org.json.JSONObject;

@AllArgsConstructor
class MessageFormatter {
    private final String nick;

    public String getJSONMessage(String content) {
        var msg = new JSONObject();
        msg.put("nick", nick);
        msg.put("message", content);
        return msg.toString();
    }
}
