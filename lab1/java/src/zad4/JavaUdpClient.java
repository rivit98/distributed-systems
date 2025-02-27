package zad4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class JavaUdpClient {
    public static void main(String[] args) {
        System.out.println("JAVA UDP CLIENT");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket()) {
            var receiveBuffer = new byte[1024];

            var address = InetAddress.getByName("localhost");
            var sendBuffer = "Ping Java".getBytes();
            var sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
            socket.send(sendPacket);

            var receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            var receivedString = new String(receiveBuffer, 0, receivePacket.getLength());
            System.out.println("Response: " + receivedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
