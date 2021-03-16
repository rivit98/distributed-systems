package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private final static int PORT = 11337;
    private final static String HOSTNAME = "localhost";
    private final static int MULTICAST_PORT = 11338;
    private final static String MULTICAST_HOST = "230.0.0.0";

    private final InetAddress address;
    private final InetAddress multicastAddress;

    public ChatClient() throws UnknownHostException {
        this.address = InetAddress.getByName(ChatClient.HOSTNAME);
        this.multicastAddress = InetAddress.getByName(ChatClient.MULTICAST_HOST);
    }

    public static void main(String[] args) throws UnknownHostException {
        var chatClient = new ChatClient();
        chatClient.run();
    }

    public void run() {
        System.out.print("Enter your nickname: ");
        var scanner = new Scanner(System.in);
        var nick = scanner.nextLine();
        var messageFormatter = new MessageFormatter(nick);

        try (
                var tcpSocket = new Socket(HOSTNAME, PORT);
                var udpSocket = new DatagramSocket(tcpSocket.getLocalPort());
                var multicastSocket = new MulticastSocket(MULTICAST_PORT);
                var out = new PrintWriter(tcpSocket.getOutputStream(), true)
        ) {
            var group = InetAddress.getByName(MULTICAST_HOST);
            multicastSocket.joinGroup(group);
//            multicastSocket.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false);
            var messageReceiver = new MessageReceiver(tcpSocket, udpSocket, multicastSocket);
            messageReceiver.run();
            System.out.println("Start chatting!");

            while (true) {
                var msg = scanner.nextLine();
                if (msg.isBlank() || msg.isEmpty()) {
                    continue;
                }

                msg = msg.strip();
                if (msg.equals("!quit")) {
                    out.println("!quit");
                    multicastSocket.leaveGroup(group);
                    break;
                } else if (msg.startsWith("!U ") && msg.length() > 3) {
                    msg = msg.substring(3).strip();
                    sendUDP(udpSocket, messageFormatter.getJSONMessage(msg));
                } else if (msg.startsWith("!M ") && msg.length() > 3) {
                    msg = msg.substring(3).strip();
                    sendMulticast(multicastSocket, messageFormatter.getJSONMessage(msg));
                } else {
                    var toSend = messageFormatter.getJSONMessage(msg);
                    out.println(toSend);
                }
                System.out.println("You: " + msg);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        System.out.println("Bye!");
    }

    private void sendUDP(DatagramSocket socket, String message) throws IOException {
        socket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, this.address, ChatClient.PORT));
    }

    private void sendMulticast(MulticastSocket socket, String message) throws IOException {
        socket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, this.multicastAddress, ChatClient.MULTICAST_PORT));
    }
}
