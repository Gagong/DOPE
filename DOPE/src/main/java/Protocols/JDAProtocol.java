package Protocols;

import Debug.Debug;
import Tasks.AutoAlertsTask;
import Tasks.WarnedDestroyTask;
import Listeners.BotListener;
import Utils.Api;
import Variables.BotKey;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.util.Timer;

public class JDAProtocol {
    public static JDA JDA;
    private static BotKey BotKey = new BotKey();

    public static void ExecuteJDAProtocol() {
        try {
            try {
                JDA = new JDABuilder(BotKey.BOT_KEY)
                        .addEventListeners(new BotListener())
                        .setActivity(Activity.playing("Online!"))
                        .build()
                        .awaitReady();
            } catch (LoginException e) {
                e.printStackTrace();
            }
            Api.Update();
            Debug.p("API", "JDA", "Finished Building JDA!");
            Thread.sleep(2000);
            try {
                Timer AutoAlertTask = new Timer(true);
                AutoAlertTask.scheduleAtFixedRate(new AutoAlertsTask(), 1000,1000 * 10);
                Timer WarnedDestroyTask = new Timer(true);
                WarnedDestroyTask.scheduleAtFixedRate(new WarnedDestroyTask(), 1000, 1000 * 60 * 60);
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("FATAL ERROR");
                Thread.sleep(5000);
                Timer AutoAlertTask = new Timer(true);
                AutoAlertTask.scheduleAtFixedRate(new AutoAlertsTask(), 1000,1000 * 10);
                Timer WarnedDestroyTask = new Timer(true);
                WarnedDestroyTask.scheduleAtFixedRate(new WarnedDestroyTask(), 1000, 1000 * 60 * 60);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
