package admin;


import common.Utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Admin {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("Admin started");

        var channel = Utils.createAndSetupChannel();

        var infoThread = new InfoReceiver("admin", channel);
        var inputThread = new InputThread(channel);

        infoThread.start();
        inputThread.start();

        infoThread.join();
        inputThread.join();
    }
}
