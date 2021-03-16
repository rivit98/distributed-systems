package server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server {
    private final static int PORT = 11337;
    private final List<Client> clients = Collections.synchronizedList(new LinkedList<>());

    public static void main(String[] args) {
        new Server().run();
    }

    public void run() {
        try (
                var serverSocket = new ServerSocket(PORT);
                var datagramSocket = new DatagramSocket(PORT)
        ) {
            System.out.println("Server listening on port: " + PORT);
            var udpReceiver = new Thread(new UDPReceiver(this, datagramSocket));
            udpReceiver.start();

            while (true) {
                var clientSocket = serverSocket.accept();
                var client = new Client(this, clientSocket, datagramSocket);
                addClient(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient(Client client) {
        System.out.println("Client connected: " + client.getSocketAddress());
        clients.add(client);
    }

    public void removeClient(Client client) {
        System.out.println("Client disconnected " + client.getSocketAddress());
        clients.remove(client);
    }

    public void sendToOthersTCP(Client sender, String message) {
        clients.stream()
                .filter(c -> !c.equals(sender))
                .forEach(c -> c.sendMessageTCP(message));
    }

    public void sendToOthersUDP(SocketAddress address, String message) {
        clients.stream()
                .filter(c -> !c.getSocketAddress().equals(address))
                .forEach(c -> c.sendMessageUDP(message));
    }
}
