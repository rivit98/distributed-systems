package common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.Map;

import static common.Utils.newPrintingConsumer;

public class BasicInfoThread extends Thread {
    private final String teamID;
    private final Channel channel;
    private final Consumer consumer;
    private final String queueName;

    public BasicInfoThread(String teamID, Channel channel, Map<String, String> exchangeRoutingMap) throws IOException {
        super("InfoThread");
        this.teamID = teamID;
        this.channel = channel;

        this.queueName = String.format("info-queue-%s", teamID);
        channel.queueDeclare(queueName, true, false, true, null);

        for (var entry : exchangeRoutingMap.entrySet()) {
            channel.queueBind(queueName, entry.getKey(), entry.getValue());
        }

        this.consumer = newPrintingConsumer(channel);
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
