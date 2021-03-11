package zad1b;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Z1_Producer {

    public static void main(String[] argv) throws Exception {
        System.out.println("Z1 PRODUCER");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        int toSend = 1;
        for (int i = 0; i < 10; i++) {

            var message = String.valueOf(toSend);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent: " + message);
            toSend ^= 4;
        }

        channel.close();
        connection.close();
    }
}
