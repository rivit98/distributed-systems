package common;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Utils {
    public static String formatProductRoutingKey(String product){
        return internalFormat(Config.PRODUCT_ROUTING_KEY_PREFIX, product);
    }

    public static String formatInfoRoutingKey(String teamID){
        return internalFormat(Config.INFO_ROUTING_KEY_PREFIX, teamID);
    }

    private static String internalFormat(String prefix, String object){
        return String.format("%s.%s", prefix, object);
    }

    public static Channel createChannel() throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection();
        return connection.createChannel();
    }
}
