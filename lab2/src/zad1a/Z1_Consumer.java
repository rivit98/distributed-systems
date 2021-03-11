package zad1a;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Z1_Consumer {

    public static void main(String[] argv) throws Exception {
        System.out.println("Z1 CONSUMER");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                var message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);
                try {
                    System.out.println("Sleep for " + message + "s");
                    var timeToSleep = Long.parseLong(message);
                    Thread.sleep(timeToSleep * 1000);
                    System.out.println("Done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (InterruptedException | NumberFormatException | IOException e) {
                    e.printStackTrace();
                }
            }
        };

        System.out.println("Waiting for messages...");
        channel.basicConsume(QUEUE_NAME, false, consumer);

//        channel.close();
//        connection.close();
    }
}
