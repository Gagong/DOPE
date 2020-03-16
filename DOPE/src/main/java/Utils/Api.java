package Utils;

import Debug.Debug;
import Handlers.CommandHandler;
import Json.GetDataClassFromJson;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import Variables.Variables;
import Variables.Channels;

public class Api extends ListenerAdapter {

    private final OkHttpClient httpClient = new OkHttpClient();
    private CreateTag Tag = new CreateTag();
    private Variables Variables;

    {
        try {
            Variables = new Variables();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Channels Channels = new Channels();

    public void onTextChannelCreate(final TextChannelCreateEvent e) {
        String ID = "";
        String ticketID = e.getChannel().getName().toString().split("ticket-")[1];
        Collection<String> MembersListInTicket = new HashSet<String>();
        for (Member m : e.getChannel().getMembers()) {
            String id = m.getUser().getId().toString();
            MembersListInTicket.add(id);
        }
        try {
            ID = Utils.FilesManager.compareMembers(MembersListInTicket);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        e.getChannel().sendMessage(
                "Hello, " + Tag.asMember(ID) + "!\n\n" +
                        "You open a new ticket:\n" +
                        "`ID: " + ticketID + "`\n" +
                        "`Time: " + formatter.format(date) + "`\n" +
                        "`Creator: " + e.getJDA().getUserById(ID).getName().toString() + " | " + e.getJDA().getUserById(ID).getId().toString() + "`\n\n" +
                        "> Support will be with You shortly (You also can tag any support to help You faster).\n" +
                        "> Please provide us with as much information as possible so that we can solve Your problem faster.\n" +
                        "> If possible - attach **screenshots**, **GIF** or **DOPE Logs** (DOPE Logs path: `%appdata%\\DOPE\\Logs`).\n\n" +
                        "To close this ticket write **&close**."
        ).queue();
        Debug.p("API", "onTextChannelCreate", "Ticket method - Done!");
    }

    public void update() throws IOException {
        Request request = new Request.Builder()
                .url("https://powerofdark.space/api/status")
                //.url("https://raw.githubusercontent.com/Gagong/DOPE/master/DOPE/status.json")
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
                        .url("https://raw.githubusercontent.com/Gagong/DOPE/master/DOPE/status.json")
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

    public void onMessageReceived(MessageReceivedEvent event) {
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

        if (!event.getChannel().getId().toString().contains(Channels.getTicketsArchive()) && event.getChannel().getName().toString().contains("ticket") && author.isBot() && author.getId().toString().contains("508391840525975553")) {
            message.delete().queue();
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
            Debug.message("GUILD CHAT", "MessageReceive", createChatString);
        }
        else if (event.isFromType(ChannelType.PRIVATE) && !bot)
        {
            PrivateChannel privateChannel = event.getPrivateChannel();
            String createChatString = author.getName() + " | " + msg;
            Debug.message("PRIVATE CHAT", "MessageReceive", createChatString);
        }
    }
}

