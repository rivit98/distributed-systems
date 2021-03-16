package admin;


import common.ThreadComposite;
import common.Utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Admin {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("Admin started");

        var channel = Utils.createAndSetupChannel();

        new ThreadComposite(
                new InfoReceiver("admin", channel),
                new InputThread(channel)
        ).run();
    }
}
