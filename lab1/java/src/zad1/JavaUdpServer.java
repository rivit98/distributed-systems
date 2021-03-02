package zad1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class JavaUdpServer {

    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            byte[] receiveBuffer = new byte[1024];

            Arrays.fill(receiveBuffer, (byte) 0);
            var receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            socket.receive(receivePacket);
            var msg = new String(receivePacket.getData());
            System.out.println("received msg: " + msg + " from: " + receivePacket.getAddress());

            var toSend = "JAVA UDP SERVER response";
            System.out.println("Sending: " + toSend);
            socket.send(new DatagramPacket(toSend.getBytes(), toSend.length(), receivePacket.getAddress(), receivePacket.getPort()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
