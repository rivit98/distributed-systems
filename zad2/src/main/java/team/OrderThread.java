package team;

import com.rabbitmq.client.Channel;
import common.Order;
import common.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static common.Config.ORDERS_EXCHANGE_NAME;

class OrderThread extends Thread {
    private final Channel channel;
    private final String teamID;

    public OrderThread(String teamID, Channel channel) {
        super("OrderThread");
        this.teamID = teamID;
        this.channel = channel;
    }

    @Override
    public void run() {
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                var product = bufferedReader.readLine().trim();
                if (product.isEmpty()) {
                    continue;
                }

                var message = new Order(teamID, product).toJSON();
                channel.basicPublish(
                        ORDERS_EXCHANGE_NAME,
                        Utils.formatProductRoutingKey(product),
                        null,
                        message.getBytes(StandardCharsets.UTF_8)
                );
                System.out.printf("%s: %s\n", teamID, product);
            } catch (IOException exception) {
                exception.printStackTrace();
                break;
            }
        }
    }
}
