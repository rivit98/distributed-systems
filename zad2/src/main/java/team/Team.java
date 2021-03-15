package team;

import com.rabbitmq.client.Channel;
import common.BasicInfoThread;
import common.Order;
import common.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static common.Config.*;


class OrderThread extends Thread{
    private final Channel channel;
    private final String teamID;

    private int orderID = 1;

    public OrderThread(String teamID, Channel channel) throws IOException {
        super("OrderThread");
        this.teamID = teamID;
        this.channel = channel;
    }

    @Override
    public void run() {
        var bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                var product = bufferedReader.readLine().trim();
                if(product.isEmpty()){
                    continue;
                }

                var message = new Order(teamID, orderID, product).toJSON();
                channel.basicPublish(
                        ORDERS_EXCHANGE_NAME,
                        Utils.formatProductRoutingKey(product),
                        null,
                        message.getBytes(StandardCharsets.UTF_8)
                );
                System.out.printf("%s: orderID=%d %s\n", teamID, orderID, product);

                orderID++;
            } catch (IOException exception) {
                exception.printStackTrace();
                break;
            }
        }
    }
}

public class Team {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        if(args.length != 1){
            System.out.println("Usage: team team_name");
            return;
        }

        var teamID = args[0].trim();
        System.out.println("Team " + teamID);

        var channel = Utils.createAndSetupChannel();

        var exchangeRoutingMap = Map.of(
                INFO_EXCHANGE_NAME, Utils.formatInfoRoutingKey(teamID),
                INFO_TEAMS_EXCHANGE_NAME, ""
        );
        var infoThread = new BasicInfoThread(teamID, channel, exchangeRoutingMap);
        var orderThread = new OrderThread(teamID, channel);

        orderThread.start();
        infoThread.start();

        orderThread.join();
        infoThread.join();
    }
}
