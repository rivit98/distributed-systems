package admin;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import common.Utils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static common.Config.INFO_EXCHANGE_NAME;
import static common.Config.ORDERS_EXCHANGE_NAME;
import static common.Utils.newPrintingConsumer;

class AdminInfoReceiver extends Thread {
    private final String teamID;
    private final Channel channel;
    private final Consumer consumer;
    private final List<String> queueNames = new LinkedList<>();


    public AdminInfoReceiver(String teamID, Channel channel) throws IOException {
        super("InfoThread");
        this.teamID = teamID;
        this.channel = channel;
        this.consumer = newPrintingConsumer(channel);

        setupQueue(ORDERS_EXCHANGE_NAME, String.format("product-%s", teamID), Utils.formatProductRoutingKey("#"));
        setupQueue(INFO_EXCHANGE_NAME, String.format("info-%s", teamID), Utils.formatInfoRoutingKey("#"));
    }

    private void setupQueue(String exchangeName, String queueName, String routingKey) throws IOException {
        channel.queueDeclare(queueName, true, false, true, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        queueNames.add(queueName);
    }

    @Override
    public void run() {
        try {
            for (var queueName : queueNames) {
                channel.basicConsume(queueName, true, consumer);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
