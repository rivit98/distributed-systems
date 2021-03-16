package client;

import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
class MessageReceiver implements Runnable {
    private static final int BUFFER_LEN = 1024;
    private final Socket serverTCPSocket;
    private final DatagramSocket datagramSocket;
    private final MulticastSocket multicastSocket;
    private final List<Thread> listeners = new ArrayList<>(3);

    private void printMessage(String prefix, String message) {
        var jsonData = new JSONObject(message);
        System.out.format("[%s] %s: %s%s", prefix, jsonData.get("nick"), jsonData.get("message"), System.lineSeparator());
    }

    private void listenTCP() {
        try {
            var TCPReader = new BufferedReader(new InputStreamReader(serverTCPSocket.getInputStream()));
            while (true) {
                var message = TCPReader.readLine();
                printMessage("TCP", message);
            }
        } catch (IOException exception) {
            System.exit(1);
        }
    }

    private void listenUDP() {
        try {
            var buffer = new byte[BUFFER_LEN];
            while (true) {
                Arrays.fill(buffer, (byte) 0);

                var datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);

                printMessage("UDP", new String(datagramPacket.getData()));
            }
        } catch (IOException exception) {
        }
    }

    private void listenMulticast() {
        try {
            var buffer = new byte[BUFFER_LEN];
            while (true) {
                Arrays.fill(buffer, (byte) 0);

                var datagramPacket = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(datagramPacket);

                printMessage("Multicast", new String(datagramPacket.getData()));
            }
        } catch (IOException exception) {
        }
    }

    @Override
    public void run() {
        listeners.add(new Thread(this::listenTCP));
        listeners.add(new Thread(this::listenUDP));
        listeners.add(new Thread(this::listenMulticast));
        listeners.forEach(Thread::start);
    }
}
