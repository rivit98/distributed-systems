package client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


class MessageReceiver extends Thread {
    private final Socket serverTCPsocket;

    MessageReceiver(Socket serverTCPsocket) {
        this.serverTCPsocket = serverTCPsocket;
    }

    @Override
    public void run(){
        try {
//            var selector = Selector.open();
//            serverTCPsocket.
            var reader = new BufferedReader(new InputStreamReader(serverTCPsocket.getInputStream()));
            for(;;){
                var message = reader.readLine();
                var jsonData = new JSONObject(message);
                System.out.println(jsonData.get("nick") + ": " + jsonData.get("message"));
                System.out.print("> ");

            }
        } catch (IOException exception) {
            exception.printStackTrace();
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
                var out = new PrintWriter(socket.getOutputStream(), true)
        ){
            var messageReceiver = new MessageReceiver(socket);
            messageReceiver.start();
            System.out.println("Start chatting!");

            for(;;){
                System.out.print("> ");
                var msg = scanner.nextLine();
                var toSend = messageFormatter.getJSONMessage(msg);
                out.println(toSend);
                System.out.println("You: " + msg);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatClient().run();
    }
}
