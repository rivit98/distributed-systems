package team;

import common.BasicInfoThread;
import common.ThreadComposite;
import common.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static common.Config.INFO_EXCHANGE_NAME;
import static common.Config.INFO_TEAMS_EXCHANGE_NAME;


public class Team {
    public static void main(String[] args) throws IOException, TimeoutException {
        if(args.length != 1){
            System.out.println("Usage: team team_name");
            return;
        }

        var teamID = args[0].trim();
        System.out.println("Team " + teamID);

        var channel = Utils.createAndSetupChannel();

        var exchangeRoutingMap = Map.of(
                INFO_EXCHANGE_NAME, Utils.formatInfoRoutingKey(teamID),
                INFO_TEAMS_EXCHANGE_NAME, ""
        );

        new ThreadComposite(
                new BasicInfoThread(teamID, channel, exchangeRoutingMap),
                new OrderThread(teamID, channel)
        ).run();
    }
}
