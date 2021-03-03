package client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.Selector;
import java.util.Scanner;


class MessageReceiver extends Thread {
    private final Socket serverTCPsocket;
    private final DatagramSocket datagramSocket;
    private final Selector selector = Selector.open();

    MessageReceiver(Socket serverTCPsocket, DatagramSocket datagramSocket) throws IOException {
        this.serverTCPsocket = serverTCPsocket;
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run(){
        try {
            var reader = new BufferedReader(new InputStreamReader(serverTCPsocket.getInputStream()));
            for(;;){
                var message = reader.readLine();
                var jsonData = new JSONObject(message);
                System.out.println(jsonData.get("nick") + ": " + jsonData.get("message"));
            }
        } catch (IOException ignored) {
        }
    }
}

class MessageFormatter {
    private final String nick;

    MessageFormatter(String nick) {
        this.nick = nick;
    }

    public String getJSONMessage(String content){
        var msg = new JSONObject();
        msg.put("nick", nick);
        msg.put("message", content);
        return msg.toString();
    }
}

public class ChatClient {
    private final static int PORT = 11337;
    private final static String hostname = "localhost";


    public void run(){
        System.out.print("Enter your nickname: ");
        var scanner = new Scanner(System.in);
        var nick = scanner.nextLine();
        var messageFormatter = new MessageFormatter(nick);

        try(
                var socket = new Socket(hostname, PORT);
                var datagramSocket = new DatagramSocket(socket.getLocalPort());
                var out = new PrintWriter(socket.getOutputStream(), true)
        ){
            var messageReceiver = new MessageReceiver(socket, datagramSocket);
            messageReceiver.start();
            System.out.println("Start chatting!");

            for(;;){
                var msg = scanner.nextLine();
                if(msg.isBlank() || msg.isEmpty()){
                    continue;
                }

                msg = msg.strip();
                if(msg.equals("!quit")){
                    out.println("!quit");
                    break;
                }else if(msg.startsWith("!U ") && msg.length() > 3){
                    msg = msg.substring(3).strip();
                    sendUDP(datagramSocket, messageFormatter.getJSONMessage(msg));
                }else if(false){

                }else{
                    var toSend = messageFormatter.getJSONMessage(msg);
                    out.println(toSend);
                }
                System.out.println("You: " + msg);
            }
        } catch (IOException ignored) {
        }
        System.out.println("Bye!");
    }

    private void sendUDP(DatagramSocket socket, String message) throws IOException {
        var address = InetAddress.getByName(ChatClient.hostname);
        socket.send(new DatagramPacket(message.getBytes(), message.length(), address, ChatClient.PORT));
    }

    public static void main(String[] args) {
        var chatClient = new ChatClient();
        chatClient.run();
    }
}
