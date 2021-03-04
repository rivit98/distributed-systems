package zad1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class JavaUdpServer {
    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            var receiveBuffer = new byte[1024];

            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);
                var packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packet);
                var msg = new String(receiveBuffer, 0, packet.getLength());
                System.out.println("received msg: " + msg + " from: " + packet.getAddress());

                var toSend = "JAVA UDP SERVER response";
                System.out.println("Sending: " + toSend);
                socket.send(new DatagramPacket(toSend.getBytes(), toSend.length(), packet.getAddress(), packet.getPort()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
