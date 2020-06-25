package com.dope.gagong.bots.Protocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Tasks.AutoAlertsTask;
import com.dope.gagong.bots.Tasks.WarnedDestroyTask;
import com.dope.gagong.bots.Listeners.BotListener;
import com.dope.gagong.bots.Utils.Api;
import com.dope.gagong.bots.Variables.BotKey;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.Timer;

public class JDAProtocol {
    public static JDA JDA;

    public static void ExecuteJDAProtocol() {
        try {
            try {
                JDA = JDABuilder.create(
                        new BotKey().BOT_KEY,
                        EnumSet.allOf(GatewayIntent.class))
                        .addEventListeners(new BotListener())
                        .setActivity(Activity.playing("Online!"))
                        .build()
                        .awaitReady();
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
            } catch (LoginException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
