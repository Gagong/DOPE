package Utils;

import Debug.Debug;
import Handlers.AlertHandler;
import Handlers.CommandHandler;
import Json.GetDataClassFromJson;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.*;
import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Timer;

public class Api extends ListenerAdapter {

    private final OkHttpClient httpClient = new OkHttpClient();

    public void buildJDA() {
        try {
            JDA jda = null;
            try {
                jda = new JDABuilder("NjM3NzE4NDcyNDAyNjY1NDcy.Xe_ERg.TFjrD5RIGd6Y6xSzKISIcWh1tuY")
                        .addEventListeners(new Api())
                        .setActivity(Activity.playing("Online!"))
                        .build();
            } catch (LoginException e) {
                e.printStackTrace();
            }
            try {
                jda.awaitReady();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished Building JDA!");
            this.update();
            Timer task = new Timer();
            task.schedule(new AlertHandler(), 0,1000 * 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() throws IOException {

        Request request = new Request.Builder()
                .url("https://powerofdark.space/api/status")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        this.serverUnaviableMethod();
                        throw new IOException("Unexpected code " + response);
                    }
                    String apiData = responseBody.string();
                    //Debug.p("API", "onResponse", apiData);
                    GetDataClassFromJson.parser(apiData);
                }
            }
            private void serverUnaviableMethod() {
                Request request = new Request.Builder()
                        .url("https://raw.githubusercontent.com/Gagong/Toshinou-Revamped/master/status.json")
                        .build();

                httpClient.newCall(request).enqueue(new Callback() {
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                            String apiData = responseBody.string();
                            //Debug.p("API", "onResponse", apiData);
                            GetDataClassFromJson.parser(apiData);
                        }
                    }
                });
            }
        });
    }

    public void onMessageReceived(MessageReceivedEvent event)
    {
        JDA jda = event.getJDA();
        User author = event.getAuthor();
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        boolean bot = author.isBot();
        CommandHandler handler = new CommandHandler();

        try {
            handler.CommandHandler(msg, event, jda);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (event.isFromType(ChannelType.TEXT) && !bot)
        {
            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();
            Member member = event.getMember();
            String name;
            if (message.isWebhookMessage())
            {
                name = author.getName();
            }
            else
            {
                name = member.getEffectiveName();
            }
            String createChatString = guild.getName() + " | " + textChannel.getName() + " | " + name + " | " + msg;
            Debug.p("GUILD CHAT", "MessageReceive", createChatString);
        }
        else if (event.isFromType(ChannelType.PRIVATE) && !bot)
        {
            PrivateChannel privateChannel = event.getPrivateChannel();
            String createChatString = author.getName() + " | " + msg;
            Debug.p("PRIVATE CHAT", "MessageReceive", createChatString);
        }
    }
}

