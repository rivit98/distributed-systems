package admin;

import com.rabbitmq.client.Channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static common.Config.*;

class InputThread extends Thread {
    private final Channel channel;
    private final Map<String, Consumer<String>> commandsMap = new HashMap<>();

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

    private void sendInfo(String exchange, String message) {
        try {
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
