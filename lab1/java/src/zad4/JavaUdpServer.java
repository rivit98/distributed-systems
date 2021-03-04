package zad4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class JavaUdpServer {
    public static void main(String[] args) {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            byte[] receiveBuffer = new byte[1024];

            while(true){
                Arrays.fill(receiveBuffer, (byte) 0);
                var receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                var msg = new String(receiveBuffer, 0, receivePacket.getLength());
                System.out.println("Received: " + msg);
                var response = "";
                if(msg.toLowerCase().contains("python")){
                    response = "Pong Python";
                }else if(msg.toLowerCase().contains("java")){
                    response = "Pong Java";
                }

                socket.send(new DatagramPacket(response.getBytes(), response.length(), receivePacket.getAddress(), receivePacket.getPort()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
