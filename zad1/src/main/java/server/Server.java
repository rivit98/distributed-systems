package server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
class Client implements Runnable {
    private final Socket socket;
    private final DatagramSocket datagramSocket;
    private final PrintWriter out;
    private final Server server;
    private final BufferedReader in;
    private final SocketAddress socketAddress;

    public Client(Server server, Socket socket, DatagramSocket datagramSocket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.datagramSocket = datagramSocket;
        this.socketAddress = socket.getRemoteSocketAddress();
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessageTCP(String message) {
        out.println(message);
    }

    public void sendMessageUDP(String message) {
        try {
            var datagram = new DatagramPacket(message.getBytes(), message.getBytes().length, socketAddress);
            datagramSocket.send(datagram);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                var msg = in.readLine();
                if (msg == null || msg.equals("!quit") || socket.isClosed()) {
                    server.removeClient(this);
                    break;
                }

                System.out.println("TCP: " + msg);
                server.sendToOthersTCP(this, msg);
            }

            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

@AllArgsConstructor
class UDPReceiver implements Runnable {
    private final Server server;
    private final DatagramSocket datagramSocket;

    @Override
    public void run() {
        var buffer = new byte[1024];
        try {
            while (true) {
                Arrays.fill(buffer, (byte) 0);
                var datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);

                var msg = new String(datagramPacket.getData());
                System.out.println("UDP: " + msg);
                server.sendToOthersUDP(datagramPacket.getSocketAddress(), msg);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

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
