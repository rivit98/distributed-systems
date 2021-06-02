package server;

import Tester.BigData;
import Tester.MediumData;
import Tester.SmallData;
import Tester.TesterIface;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class TesterImpl implements TesterIface{

    private static <T> void iterateAndIgnore(List<T> arr){
        long i = 0;
        for(T v : arr){
            i++;
        }
    }

    @Override
    public void processSmall(SmallData smallData, Current current) {
        iterateAndIgnore(Arrays.stream(smallData.iSeq1).boxed().collect(Collectors.toList()));
    }

    @Override
    public void processMedium(MediumData mediumData, Current current) {
        processSmall(mediumData.smallData, current);

        iterateAndIgnore((Arrays.stream(mediumData.sSeq1).collect(Collectors.toList())));
    }

    @Override
    public void processBig(BigData bigData, Current current) {
        processMedium(bigData.mediumData, current);

        iterateAndIgnore(Arrays.stream(bigData.iSeq2).boxed().collect(Collectors.toList()));
        iterateAndIgnore(Arrays.stream(bigData.sSeq2).collect(Collectors.toList()));
        iterateAndIgnore(Arrays.stream(bigData.dSeq1).boxed().collect(Collectors.toList()));
        iterateAndIgnore(Arrays.stream(bigData.dSeq2).boxed().collect(Collectors.toList()));
    }
}

public class Server {
    public static void main(String[] args) {
        var app = new Server();
        app.run(args);
    }

    public void run(String[] args) {
        try (var communicator = Util.initialize(args)) {
            var adapter = communicator.createObjectAdapter("Adapter1");
            var printerServant = new TesterImpl();
            adapter.add(printerServant, new Identity("tester1", "tester"));
            adapter.activate();

            System.out.println("Entering event processing loop...");
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(1);
    }
}
