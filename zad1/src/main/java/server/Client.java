package server;

import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketAddress;

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
        }
    }
}
