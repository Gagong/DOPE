package Tasks;

import Debug.Debug;
import Json.JSONDataParser;
import Utils.Api;
import Protocols.JDAProtocol;
import Variables.Channels;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import java.io.IOException;
import java.util.Objects;
import java.util.TimerTask;

public class AutoAlertsTask extends TimerTask {
    private String LAST_ALERT = "";
    private boolean IS_OUTDATED = false;
    private boolean IS_MAINTENANCE = false;

    private boolean SET_GAME_ONLINE = false;
    private boolean PUSH_DO_UPDATE_ALERT = false;
    private boolean PUSH_MAINTENANCE_ALERT = false;
    private boolean PUSH_DOPE_UPDATE_ALERT = false;

    private Channels Channels = new Channels();
    private static JDA jda = JDAProtocol.JDA;

    @Override
    public void run() {
        if (SET_GAME_ONLINE)
            jda.getPresence().setActivity(Activity.playing("Online!"));
        else
            jda.getPresence().setActivity(Activity.playing("Offline!"));

        if (PUSH_DO_UPDATE_ALERT) {
            Objects.requireNonNull(jda.getTextChannelById(Channels.NEWS)).sendMessage("@everyone\n" +
                    "Darkorbit pushed a new update. Bot is Offline!\n" +
                    "Please be patient while our Developers are working on the update!").queue();
            PUSH_DO_UPDATE_ALERT = false;
        }
        else if (PUSH_MAINTENANCE_ALERT) {
            Objects.requireNonNull(jda.getTextChannelById(Channels.NEWS)).sendMessage("@everyone\n" + LAST_ALERT).queue();
            PUSH_MAINTENANCE_ALERT = false;
        }
        else if (PUSH_DOPE_UPDATE_ALERT) {
            Objects.requireNonNull(jda.getTextChannelById(Channels.NEWS)).sendMessage("@everyone\n" + LAST_ALERT).queue();
            PUSH_DOPE_UPDATE_ALERT = false;
        }

        try {
            Api.Update();
            if (!JSONDataParser.GAME_VERSION.equals(JSONDataParser.GAME_REMOTE) && this.compateLastAlert() && !IS_OUTDATED) {
                this.updateLastAlert();
                PUSH_DO_UPDATE_ALERT = true;
                SET_GAME_ONLINE = false;
                IS_OUTDATED = true;
                Debug.p("AutoAlertsTask", "DO_Update_Checker", "DO Update!");
            }
            else if (JSONDataParser.GAME_VERSION.equals(JSONDataParser.GAME_REMOTE)) {
                SET_GAME_ONLINE = true;
            }

            if (JSONDataParser.MAINTENANCE != null && !IS_MAINTENANCE && !JSONDataParser.BREAKING_NEWS.equals("") && this.compateLastAlert()) {
                this.updateLastAlert();
                IS_MAINTENANCE = true;
                PUSH_MAINTENANCE_ALERT = true;
                Debug.p("AutoAlertsTask", "Alert_Checker", "Maintence!");
            }
            else if (!JSONDataParser.BREAKING_NEWS.equals("") && this.compateLastAlert() && JSONDataParser.MAINTENANCE == null) {
                this.updateLastAlert();
                PUSH_DOPE_UPDATE_ALERT = true;
                Debug.p("AutoAlertsTask", "Alert_Checker", "Alert!");
            }

            if (JSONDataParser.MAINTENANCE == null && IS_MAINTENANCE)
                IS_MAINTENANCE = false;
            if (JSONDataParser.GAME_VERSION.equals(JSONDataParser.GAME_REMOTE) && IS_OUTDATED) {
                Debug.p("VPS AutoUpdate", "AutoUpdate", "Update Started!");
                Process p = Runtime.getRuntime().exec("screen -S xDD -dm ./xUPD.sh");
                IS_OUTDATED = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLastAlert() {
        LAST_ALERT = JSONDataParser.BREAKING_NEWS;
    }

    private boolean compateLastAlert() {
        return !JSONDataParser.BREAKING_NEWS.equals(LAST_ALERT);
    }
}
