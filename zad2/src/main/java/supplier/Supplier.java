package supplier;

import com.rabbitmq.client.*;
import common.BasicInfoThread;
import common.Order;
import common.Utils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static common.Config.*;


class SupplyThread extends Thread{
    private final Channel channel;
    private final Consumer consumer;
    private final List<String> queueNames = new LinkedList<>();
    private final String supplierID;

    public SupplyThread(String supplierID, Channel channel, List<String> availableProducts) throws IOException {
        super("SupplyThread");
        this.supplierID = supplierID;
        this.channel = channel;


        for(var product : availableProducts){
            var routingKey = Utils.formatProductRoutingKey(product);
            var queueName = String.format("product-queue-%s", product);

            channel.queueDeclare(queueName, true, false, true, null);
            channel.queueBind(queueName, ORDERS_EXCHANGE_NAME, routingKey);
            channel.basicQos(1);

            queueNames.add(queueName);
        }

        this.consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                var message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received order " + message);

                channel.basicAck(envelope.getDeliveryTag(), false);

                var order = Order.fromJSON(new JSONObject(message));
                var response = String.format("%s: Order %d (%s) completed!", supplierID, order.getOrderID(), order.getProduct());
                channel.basicPublish(
                        INFO_EXCHANGE_NAME,
                        Utils.formatInfoRoutingKey(order.getTeamID()),
                        null,
                        response.getBytes(StandardCharsets.UTF_8)
                );
            }
        };
    }

    @Override
    public void run() {
        try {
            for(var queueName : queueNames){
                channel.basicConsume(queueName, false, consumer);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

public class Supplier {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        if(args.length < 2){
            System.out.println("Usage: supplier supplier_name products...");
            return;
        }

        var supplierID = args[0].trim();
        var availableProducts = Arrays.stream(args).skip(1).collect(Collectors.toList());
        System.out.println("Supplier " + supplierID + " | " + availableProducts.toString());

        var channel = Utils.createAndSetupChannel();

        var exchangeRoutingMap = Map.of(
                INFO_SUPPLIERS_EXCHANGE_NAME, ""
        );
        var infoThread = new BasicInfoThread(supplierID, channel, exchangeRoutingMap);
        var supplyThread = new SupplyThread(supplierID, channel, availableProducts);

        infoThread.start();
        supplyThread.start();

        infoThread.join();
        supplyThread.join();
    }
}
