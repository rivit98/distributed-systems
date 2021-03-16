package server;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

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

                var msg = new String(buffer, 0, datagramPacket.getLength());
                System.out.println("UDP: " + msg);
                server.sendToOthersUDP(datagramPacket.getSocketAddress(), msg);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
