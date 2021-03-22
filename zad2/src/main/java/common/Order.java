package common;

import lombok.Data;
import org.json.JSONObject;

@Data
public class Order {
    private final String teamID;
    private final String product;

    public String toJSON() {
        var json = new JSONObject();
        json.put("teamID", teamID);
        json.put("product", product);
        return json.toString();
    }

    public static Order fromJSON(JSONObject json){
        return new Order(json.getString("teamID"), json.getString("product"));
    }
}
