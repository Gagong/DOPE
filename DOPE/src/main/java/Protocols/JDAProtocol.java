package Protocols;

import Debug.Debug;
import Tasks.AutoAlertsTask;
import Tasks.WarnedDestroyTask;
import Listeners.BotListener;
import Utils.Api;
import Variables.Variables;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.util.Timer;

public class JDAProtocol {
    public static JDA JDA;
    private static Variables Variables = new Variables();

    public static void ExecuteJDAProtocol() {
        try {
            try {
                JDA = new JDABuilder(Variables.BOT_KEY)
                        .addEventListeners(new BotListener())
                        .setActivity(Activity.playing("Online!"))
                        .build()
                        .awaitReady();
            } catch (LoginException e) {
                e.printStackTrace();
            }
            Debug.p("API", "JDA", "Finished Building JDA!");
            Api.Update();
            Thread.sleep(1000);

            Timer AutoAlertTask = new Timer(true);
            AutoAlertTask.scheduleAtFixedRate(new AutoAlertsTask(), 0,1000 * 10);

            Timer WarnedDestroyTask = new Timer(true);
            WarnedDestroyTask.scheduleAtFixedRate(new WarnedDestroyTask(), 0, 1000 * 60 * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
