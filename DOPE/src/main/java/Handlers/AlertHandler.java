package Handlers;

import Debug.Debug;
import Json.DataBase;
import Json.GetDataClassFromJson;
import Utils.Api;
import Utils.FilesManager;
import Variables.Channels;
import Variables.Roles;
import Variables.Variables;
import com.google.gson.Gson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlertHandler extends TimerTask {
    private String lastAlert = "";
    private boolean isOutdated = false;
    private boolean isMaintence = false;

    private boolean setGameOnline = false;
    private boolean pushDOUpdateAlert = false;
    private boolean pushMaintenceAlert = false;
    private boolean pushDOPEUpdateAlert = false;

    private Channels Channels = new Channels();
    private Roles Roles = new Roles();
    private Variables Variables;
    private FilesManager FilesManager = new FilesManager();

    {
        try {
            Variables = new Variables();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private JDA jda;
    {
        try {
            jda = new JDABuilder(Variables.getBotKey()).build().awaitReady();
            Debug.p("AlertHandler", "JDA", "Finished Building 2 JDA!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty("user.dir") + "/Users"))) {
            List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".txt")).collect(Collectors.toList());

            result.forEach(warnedFile -> {
                Gson gson = new Gson();
                DataBase data = null;
                try {
                    data = gson.fromJson(FilesManager.readJson(warnedFile).toString(), DataBase.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Instant instant = Instant.parse(data.warnedTime);
                Duration timeElapsed = Duration.between(instant, Instant.now());

                if (timeElapsed.toHours() >= 24) {
                    jda.getGuildById(Channels.getServer()).removeRoleFromMember(jda.getGuildById(Channels.getServer()).getMember(jda.getUserById(data.ID)), jda.getRoleById(Roles.getWarned())).queue();
                    EmbedBuilder log = new EmbedBuilder();
                    log.setTitle("SYNC: Removed Warned role event!");
                    log.setDescription("Removed from: " + data.userName + "\nID: " + data.ID + "\nWarned Time:" + data.warnedTime);
                    log.setAuthor("DOPE MODERATION", null, null);
                    log.setColor(Color.green);
                    log.setTimestamp(Instant.now());
                    jda.getTextChannelById(Channels.getWarnedArchive()).sendMessage(log.build()).queue();
                    FilesManager.tryToDeleteFile(warnedFile);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Api _a = new Api();
        if (this.isSetGameOnline()) {
            jda.getPresence().setActivity(Activity.playing("Online!"));
        }
        else {
            jda.getPresence().setActivity(Activity.playing("Offline!"));
        }
        if (isPushDOUpdateAlert()) {
            jda.getTextChannelById(Channels.getNews()).sendMessage("@everyone\n" +
                    "Darkorbit pushed a new update. Bot is Offline!\n" +
                    "Please be patient while our Developers are working on the update!").queue();
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
            if (GetDataClassFromJson.get_data21().equals(GetDataClassFromJson.get_data24()) && isOutdated) {
                isOutdated = false;
                Process process = Runtime.getRuntime().exec("xUPD.sh");
                /*EmbedBuilder log = new EmbedBuilder();
                log.setTitle("SYNC: Found DOPE update!");
                log.setDescription("State: Run AutoUpdater on VPS!");
                log.setAuthor("DOPE VPS INFO", null, null);
                log.setColor(Color.green);
                log.setTimestamp(Instant.now());
                jda.getTextChannelById(Channels.getVPSManagment()).sendMessage(log.build()).queue();*/
            }
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
