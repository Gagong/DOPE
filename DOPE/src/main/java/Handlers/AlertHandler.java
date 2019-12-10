package Handlers;

import Debug.Debug;
import Json.GetDataClassFromJson;
import Utils.Api;
import Variables.Channels;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.TimerTask;

public class AlertHandler extends TimerTask {
    private String lastAlert = "";
    private boolean isOutdated = false;
    private boolean isMaintence = false;

    private boolean setGameOnline = false;
    private boolean pushDOUpdateAlert = false;
    private boolean pushMaintenceAlert = false;
    private boolean pushDOPEUpdateAlert = false;

    private Channels Channels = new Channels();

    public void run() {
        Api _a = new Api();
        try {
            JDA jda = new JDABuilder("NjM3NzE4NDcyNDAyNjY1NDcy.Xe_edQ.BpbyuVh-RMX8QL0qv97Bn10a56Y").build().awaitReady();
            if (this.isSetGameOnline()) {
                jda.getPresence().setActivity(Activity.playing("Online!"));
            }
            else {
                jda.getPresence().setActivity(Activity.playing("Offline!"));
            }
            if (isPushDOUpdateAlert()) {
                jda.getTextChannelById(Channels.getNews()).sendMessage("@everyone\n" +
                        "Darkorbit pushed a new update. Bot is Offline!\n" +
                        "Please be patient while the Developers are working on the update!").queue();
                pushDOUpdateAlert = false;
            }
            else if (isPushMaintenceAlert()) {
                jda.getTextChannelById(Channels.getNews()).sendMessage("@everyone\n" + lastAlert).queue();
                pushMaintenceAlert = false;
            }
            else if (isPushDOPEUpdateAlert()) {
                jda.getTextChannelById(Channels.getNews()).sendMessage("@everyone\n" + lastAlert).queue();
                pushDOPEUpdateAlert = false;
            }
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            _a.update();
            if (!GetDataClassFromJson.get_data21().equals(GetDataClassFromJson.get_data24()) && this.compateLastAlert() && !isOutdated) {
                this.updateLastAlert();
                pushDOUpdateAlert = true;
                setGameOnline = false;
                isOutdated = true;
                Debug.p("AlertHandler", "DO_Update_Checker", "DO Update!");
            }
            else if (GetDataClassFromJson.get_data21().equals(GetDataClassFromJson.get_data24())) {
                setGameOnline = true;
            }

            if (GetDataClassFromJson.get_data5() != null && !isMaintence && !GetDataClassFromJson.get_data4().equals("") && this.compateLastAlert()) {
                this.updateLastAlert();
                isMaintence = true;
                pushMaintenceAlert = true;
                Debug.p("AlertHandler", "Alert_Checker", "Maintence!");
            }
            else if (!GetDataClassFromJson.get_data4().equals("") && this.compateLastAlert() && GetDataClassFromJson.get_data5() == null) {
                this.updateLastAlert();
                pushDOPEUpdateAlert = true;
                Debug.p("AlertHandler", "Alert_Checker", "Alert!");
            }

            if (GetDataClassFromJson.get_data5() == null && isMaintence)
                isMaintence = false;
            if (GetDataClassFromJson.get_data21().equals(GetDataClassFromJson.get_data24()) && isOutdated)
                isOutdated = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLastAlert() {
        lastAlert = GetDataClassFromJson.get_data4();
    }

    private boolean compateLastAlert() {
        if (!GetDataClassFromJson.get_data4().equals(lastAlert))
            return true;
        else
            return false;
    }

    public boolean isPushDOUpdateAlert() {
        return pushDOUpdateAlert;
    }

    public boolean isSetGameOnline() {
        return setGameOnline;
    }

    public boolean isPushMaintenceAlert() {
        return pushMaintenceAlert;
    }

    public boolean isPushDOPEUpdateAlert() {
        return pushDOPEUpdateAlert;
    }
}
