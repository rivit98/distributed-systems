package supplier;

import common.BasicInfoThread;
import common.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static common.Config.INFO_SUPPLIERS_EXCHANGE_NAME;


public class Supplier {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        if(args.length < 2){
            System.out.println("Usage: supplier supplier_name products...");
            return;
        }

        var supplierID = args[0].trim();
        var availableProducts = Arrays.stream(args).skip(1).collect(Collectors.toList());
        System.out.println("Supplier " + supplierID + " | " + availableProducts.toString());

        var channel = Utils.createAndSetupChannel();

        var exchangeRoutingMap = Map.of(
                INFO_SUPPLIERS_EXCHANGE_NAME, ""
        );
        var infoThread = new BasicInfoThread(supplierID, channel, exchangeRoutingMap);
        var supplyThread = new SupplyThread(supplierID, channel, availableProducts);

        infoThread.start();
        supplyThread.start();

        infoThread.join();
        supplyThread.join();
    }
}
