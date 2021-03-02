package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Client extends Thread {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final Server server;

    public Client(Server server, Socket socket) throws IOException {
        super();
        this.server = server;
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            for(;;){
                var msg = in.readLine();
                server.sendToOthers(this, msg);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}


public class Server {
    private final static int PORT = 11337;
    private final List<Client> clients = Collections.synchronizedList(new LinkedList<>());

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started");
            while(true){
                var clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                var client = new Client(this, clientSocket);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToOthers(Client sender, String message){
        clients.stream().filter(c -> c != sender).forEach(c -> c.sendMessage(message.toString()));
    }

    public static void main(String[] args) {
        new Server().run();
    }
}
