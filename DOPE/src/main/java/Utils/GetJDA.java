package Utils;

import Debug.Debug;
import Handlers.AlertHandler;
import Variables.Variables;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;

public class GetJDA {

    private JDA jda;
    private Variables Variables;

    {
        try {
            Variables = new Variables();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void buildJDA() {
        Api _api = new Api();
        try {
            try {
                jda = new JDABuilder(Variables.getBotKey())
                        .addEventListeners(_api)
                        .setActivity(Activity.playing("Online!"))
                        .build()
                        .awaitReady();
            } catch (LoginException e) {
                e.printStackTrace();
            }
            Debug.p("API", "JDA", "Finished Building JDA!");
            _api.update();
            Timer task = new Timer(true);
            task.scheduleAtFixedRate(new AlertHandler(), 0,1000 * 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JDA getJDA() {
        return jda;
    }
}
