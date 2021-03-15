package team;

import com.rabbitmq.client.*;
import common.Order;
import common.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import static common.Config.INFO_EXCHANGE_NAME;
import static common.Config.ORDERS_EXCHANGE_NAME;


class OrderThread extends Thread{
    private final Channel channel;
    private final String teamID;

    private int orderID = 1;

    public OrderThread(String teamID, Channel channel) throws IOException {
        super("OrderThread");
        this.teamID = teamID;
        this.channel = channel;

        channel.exchangeDeclare(ORDERS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    }

    @Override
    public void run() {
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                System.out.print("> ");
                var product = bufferedReader.readLine().trim();

                var message = new Order(teamID, orderID, product).toJSON();
                channel.basicPublish(
                        ORDERS_EXCHANGE_NAME,
                        Utils.formatProductRoutingKey(product),
                        null,
                        message.getBytes(StandardCharsets.UTF_8)
                );
                System.out.println("Sent order: " + message);

                orderID++;
            } catch (IOException exception) {
                exception.printStackTrace();
                break;
            }
        }
    }
}

class InfoThread extends Thread{
    private final String teamID;
    private final Channel channel;
    private final Consumer consumer;
    private final String queueName;


    public InfoThread(String teamID, Channel channel) throws IOException {
        super("InfoThread");
        this.teamID = teamID;
        this.channel = channel;

        channel.exchangeDeclare(INFO_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        this.queueName = String.format("info-queue-%s", teamID);
        channel.queueDeclare(queueName, true, false, true, null);

        var routingKey = Utils.formatInfoRoutingKey(teamID);
        channel.queueBind(queueName, INFO_EXCHANGE_NAME, routingKey);
        channel.basicQos(1);

        this.consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                var message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Info: " + message);
            }
        };
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}


public class Team {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
//        if(args.length != 2){
//            System.out.println("Usage: team team_name");
//            return;
//        }

        args = new String[]{"asdf", "consumer" + new Random().nextInt(100)};

        var teamID = args[1].trim();
        System.out.println("Starting " + teamID);

        var channel = Utils.createChannel();
        var orderThread = new OrderThread(teamID, channel);
        var infoThread = new InfoThread(teamID, channel);

        orderThread.start();
        infoThread.start();

        orderThread.join();
        infoThread.join();
    }
}
