package admin;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import common.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static common.Config.*;
import static common.Utils.newPrintingConsumer;

class InfoReceiver extends Thread {
    private final String teamID;
    private final Channel channel;
    private final Consumer consumer;
    private final List<String> queueNames = new LinkedList<>();


    public InfoReceiver(String teamID, Channel channel) throws IOException {
        super("InfoThread");
        this.teamID = teamID;
        this.channel = channel;
        this.consumer = newPrintingConsumer(channel);

        setupQueue(ORDERS_EXCHANGE_NAME, String.format("product-queue-%s", teamID), Utils.formatProductRoutingKey("#"));
        setupQueue(INFO_EXCHANGE_NAME, String.format("info-queue-%s", teamID), Utils.formatInfoRoutingKey("#"));
    }

    private void setupQueue(String exchangeName, String queueName, String routingKey) throws IOException {
        channel.queueDeclare(queueName, true, false, true, null);
        channel.queueBind(queueName, ORDERS_EXCHANGE_NAME, routingKey);
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

class InputThread extends Thread {
    private final Channel channel;
    private final Map<String, java.util.function.Consumer<String>> commandsMap = new HashMap<>();

    public InputThread(Channel channel) {
        super("InputThread");
        this.channel = channel;

        commandsMap.put("all", this::sendToAll);
        commandsMap.put("a", this::sendToAll);
        commandsMap.put("suppliers", this::sendToSuppliers);
        commandsMap.put("s", this::sendToSuppliers);
        commandsMap.put("teams", this::sendToTeams);
        commandsMap.put("t", this::sendToTeams);
    }

    private void sendToAll(String message) {
        sendInfo(INFO_ALL_EXCHANGE_NAME, message);
    }

    private void sendToTeams(String message) {
        sendInfo(INFO_TEAMS_EXCHANGE_NAME, message);
    }

    private void sendToSuppliers(String message) {
        sendInfo(INFO_SUPPLIERS_EXCHANGE_NAME, message);
    }

    private void sendInfo(String exchange, String message){
        try{
            channel.basicPublish(exchange, "", null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                var message = bufferedReader.readLine().trim();
                var parts = message.split(" ", 2);

                if (parts.length != 2) {
                    System.out.println("Invalid command");
                    System.out.println("all|suppliers|teams message");
                    continue;
                }

                var action = commandsMap.get(parts[0]);
                if (action == null) {
                    System.out.println("Invalid target. Choose one: all|suppliers|teams");
                    continue;
                }

                action.accept(parts[1]);

            } catch (IOException exception) {
                exception.printStackTrace();
                break;
            }
        }
    }
}

public class Admin {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("Admin started");

        var channel = Utils.createAndSetupChannel();

        var infoThread = new InfoReceiver("admin", channel);
        var inputThread = new InputThread(channel);

        infoThread.start();
        inputThread.start();

        infoThread.join();
        inputThread.join();
    }
}
