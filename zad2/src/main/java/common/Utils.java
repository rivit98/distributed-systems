package common;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static common.Config.*;

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

    public static Channel createAndSetupChannel() throws IOException, TimeoutException {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");
        var connection = factory.newConnection();
        var channel = connection.createChannel();
        setUpExchanges(channel);
        return channel;
    }

    public static Consumer newPrintingConsumer(Channel channel){
        return new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                var message = new String(body, StandardCharsets.UTF_8);
                System.out.println(message);
            }
        };
    }

    public static void setUpExchanges(Channel channel) throws IOException {
        // products
        channel.exchangeDeclare(ORDERS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // info system
        channel.exchangeDeclare(INFO_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(INFO_TEAMS_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(INFO_SUPPLIERS_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(INFO_ALL_EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.exchangeBind(INFO_TEAMS_EXCHANGE_NAME, INFO_ALL_EXCHANGE_NAME, "");
        channel.exchangeBind(INFO_SUPPLIERS_EXCHANGE_NAME, INFO_ALL_EXCHANGE_NAME, "");
    }
}

