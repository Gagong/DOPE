package Handlers;

import Debug.Debug;
import Json.GetDataClassFromJson;
import Utils.Api;
import Utils.CreateTag;
import Variables.Users;
import Variables.Variables;
import Variables.Roles;
import Variables.Channels;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.TimerTask;

public class AlertHandler extends TimerTask {
    private String lastAlert = "";//GetDataClassFromJson.get_data4();
    private boolean isOutdated = false;
    private boolean isMaintence = false;

    private boolean setGameOnline = false;
    private boolean pushDOUpdateAlert = false;
    private boolean pushMaintenceAlert = false;
    private boolean pushDOPEUpdateAlert = false;

    private CreateTag Tag = new CreateTag();
    private Users Users = new Users();
    private Variables Variables = new Variables();
    private Roles Roles = new Roles();
    private Channels Channels = new Channels();

    //{"EnabledGG":false,"Versions":{"game":"Server unaviable","cli":"Server unaviable","dope":"Server unaviable","game_remote":"Server unaviable"},"ProtocolOutOfDate":false,"BreakingNews":["Server restarting..."],"Maintenance":null}

    public void run() {
        Api _a = new Api();
        try {
            JDA jda = new JDABuilder("NjM3NzE4NDcyNDAyNjY1NDcy.Xc22wQ.ufRnAYqPgSNOQTCyh54ms_6BKrc").build().awaitReady();
            if (this.isSetGameOnline()) {
                jda.getPresence().setActivity(Activity.playing("Online!"));
            }
            else {
                jda.getPresence().setActivity(Activity.playing("Offline!"));
            }
            if (isPushDOUpdateAlert()) {
                //jda.getTextChannelById(Channels.getNews()).sendMessage("DO Update found!").queue(); 637718328978178091
                jda.getTextChannelById("637718328978178091").sendMessage("DO Update found!").queue();
                pushDOUpdateAlert = false;
            }
            else if (isPushMaintenceAlert()) {
                jda.getTextChannelById("637718328978178091").sendMessage("Maintence alert!").queue();
                pushMaintenceAlert = false;
            }
            else if (isPushDOPEUpdateAlert()) {
                //jda.getTextChannelById("637718328978178091").sendMessage("DOPE alert!").queue();
                pushDOPEUpdateAlert = false;
            }
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            _a.update();
            Debug.p("AlertHandler", "run", "Api update!");
            if (!GetDataClassFromJson.get_data21().equals(GetDataClassFromJson.get_data24()) && this.compateLastAlert() && isOutdated) {
                this.updateLastAlert();
                pushDOUpdateAlert = true;
                setGameOnline = false;
                Debug.p("AlertHandler", "DO_Update_Checker", "DO Update!");
            }
            else if (GetDataClassFromJson.get_data21().equals(GetDataClassFromJson.get_data24()) && !isOutdated) {
                setGameOnline = true;
                isOutdated = true;
            }

            if (GetDataClassFromJson.get_data5() != null && !isMaintence && !GetDataClassFromJson.get_data4().equals("") && this.compateLastAlert()) {
                this.updateLastAlert();
                isMaintence = true;
                pushMaintenceAlert = true;
                Debug.p("AlertHandler", "News Alerter", "Maintence!");
            }
            else if (!GetDataClassFromJson.get_data4().equals("") && this.compateLastAlert() && GetDataClassFromJson.get_data5() == null ) {
                this.updateLastAlert();
                pushDOPEUpdateAlert = true;
                Debug.p("AlertHandler", "News Alerter", "New Alert!");
            }

            if (GetDataClassFromJson.get_data5() == null && isMaintence)
                isMaintence = false;
            if (GetDataClassFromJson.get_data21().equals(GetDataClassFromJson.get_data24()) && isOutdated)
                isOutdated = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(lastAlert);
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
