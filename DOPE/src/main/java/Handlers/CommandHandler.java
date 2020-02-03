package Handlers;

import Debug.Debug;
import Utils.Api;
import Json.GetDataClassFromJson;
import Utils.CreateTag;
import Utils.FilesManager;
import Variables.Users;
import Variables.Variables;
import Variables.Roles;
import Variables.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CommandHandler {

    private Api _api = new Api();
    private String prefix = "!";
    private CreateTag Tag = new CreateTag();
    private Users Users = new Users();
    private Variables Variables;

    {
        try {
            Variables = new Variables();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private Roles Roles = new Roles();
    private Channels Channels = new Channels();
    private FilesManager FilesManager = new FilesManager();

    public void CommandHandler (String command, MessageReceivedEvent event, JDA jda) throws Exception {

        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        this.pingDevs(message, channel, event, author);
        this.addSupport(message, author, command, channel);
        this.addBotKey(author, command, channel, event);
        if (command.startsWith(prefix))
        {
            _api.update();
            this.handler(command, author, message, channel, jda);
        }
    }

    private void handler (String command, User author, Message message, MessageChannel channel, JDA jda) throws IOException {
        if (command.equalsIgnoreCase("!ping")) {
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!")
                    .queue(response -> {
                        response.editMessageFormat("Pong: %d ms!", System.currentTimeMillis() - time).queue();
                    });
        }
        else if (command.equalsIgnoreCase("!roll")) {
            Random rand = new Random();
            int roll = rand.nextInt(10) + 1;
            channel.sendMessage("Your roll: " + roll).queue();
        }
        else if (command.equalsIgnoreCase("!squad")) {
            String TAG[] = {
                    Tag.asMember(Users.getCrankTV()),
                    Tag.asMember(Users.getKewai()),
                    Tag.asMember(Users.getZhoiak()),
                    Tag.asMember(Users.getAD3RTRON()),
                    Tag.asMember(Users.getSumi()),
                    Tag.asMember(Users.getEra()),
                    Tag.asMember(Users.getGagong())
            };
            if (message.getMember().getRoles().toString().contains(Roles.getSquad())) {
                String squad = "";
                for (String id: TAG) {
                    squad += id + " ";
                }
                channel.sendMessage("WHERE MY SQUAD? " + squad).queue();
            }
        }
        else if (command.equalsIgnoreCase("!love")) {
            channel.sendMessage(":heart: **We love DOPE** :heart:").queue();
        }
        else if (command.equalsIgnoreCase("!hangars")) {
            channel.sendMessage("Please, setup Hangars if you use Palladium module!\nhttps://ibb.co/WDzq2sb").queue();
        }
        else if (command.equalsIgnoreCase("!perkava")) {
            channel.sendMessage("Perkava preview:\nhttps://discordapp.com/channels/598177730890039298/606417989293572096/608010142230773770").queue();
        }
        else if (command.equalsIgnoreCase("!logs")) {
            channel.sendMessage("DOPE Logs path: `%appdata%\\DOPE\\Logs`\nhttps://cdn.discordapp.com/attachments/598182739228753941/663360155941076992/unknown.png").queue();
        }
        else if (command.equalsIgnoreCase("!link")) {
            String link = "Web URL - **" + Variables.getWebURL() + "**";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!invite")) {
            channel.sendMessage(Variables.getDiscord()).queue();
        }
        else if (command.equalsIgnoreCase("!status")) {
            String v1 = GetDataClassFromJson.get_data21();
            String v2 = GetDataClassFromJson.get_data24();
            if (v1.equals(v2)) {
                channel.sendMessage("Bot status: **Online!**").queue();
                jda.getPresence().setActivity(Activity.playing("Online!"));
            }
            else {
                channel.sendMessage("Bot Status: **Offline!**").queue();
                jda.getPresence().setActivity(Activity.playing("Offline!"));
            }
        }
        else if (command.equalsIgnoreCase("!info")) {
            String v = GetDataClassFromJson.get_data23();
            channel.sendMessage("> **DarkOrbit Packet Bot Experiment v." + v + ".**\n" +
                    "**Web links**\n" +
                    "-> Web URL: **" + Variables.getWebURL() + "**\n" +
                    "-> Download: " + Variables.getDownloadURL() + "\n" +
                    "-> Bot Panel: " + Variables.getBotPanel() + "\n" +
                    "-> Licenses: " + Variables.getLicenses() + "\n\n" +

                    "**Discord info**\n" +
                    "-> For quick start - read " + Tag.asChannel(Channels.getWindowsBotGuide()) + " and "+ Tag.asChannel(Channels.getLinuxBotGuide()) +" channels.\n" +
                    "-> To buy license - read " + Tag.asChannel(Channels.getPaymentMethods()) + " channel.\n" +
                    "-> If you need help - we have support team. Open a new ticket in " + Tag.asChannel(Channels.getSupport()) + " channel.\n" +
                    "-> If you found a bug - make a report in " + Tag.asChannel(Channels.getBugReports()) + " channel.\n" +
                    "-> Check out our profile templates " + Tag.asChannel(Channels.getProfileTemplates()) + " channel.\n" +
                    "-> Giveaways every month! Do not miss your opportunity to participate! " + Tag.asChannel(Channels.getGiveaway()) + " channel.\n" +
                    "-> Check out our staff marketplace in " + Tag.asChannel(Channels.getMarketplace()) + " channel."
            ).queue();
        }
        else if (command.equalsIgnoreCase("!download")) {
            String link = "Latest DOPE versions:\n" +
                    "Windows x64 - **" + Variables.getWindows64() + "**\n" +
                    "Windows x86 - **" + Variables.getWindows86() + "**\n" +
                    "Linux x64 - **" + Variables.getLinux64() + "**\n" +
                    "Linux ARM - **" + Variables.getLinuxARM() + "**\n";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!download w")) {
            String link = "Latest Windows DOPE versions:\n" +
                    "Windows x64 - **" + Variables.getWindows64() + "**\n" +
                    "Windows x86 - **" + Variables.getWindows86() + "**\n";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!download l")) {
            String link = "Latest Linux DOPE versions:\n" +
                    "Linux x64 - **" + Variables.getLinux64() + "**\n" +
                    "Linux ARM - **" + Variables.getLinuxARM() + "**\n";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!download w x64")) {
            String link = "Windows x64 - **" + Variables.getWindows64() + "**";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!download w x86")) {
            String link = "Windows x86 - **" + Variables.getWindows86() + "**";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!download l x64")) {
            String link = "Linux x64 - **" + Variables.getLinux64() + "**";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!download l arm")) {
            String link = "Linux ARM - **" + Variables.getLinuxARM() + "**";
            channel.sendMessage(link).queue();
        }
        else if (command.equalsIgnoreCase("!version")) {
            EmbedBuilder version = new EmbedBuilder();
            version.setTitle("DOPE | Versions info", Variables.getDownloadURL());
            version.setAuthor(author.getName(), null, author.getAvatarUrl());
            version.setColor(Color.CYAN);
            version.setDescription("Make sure what You use latest DOPE version!");
            version.addField("Current game version", GetDataClassFromJson.get_data21(), false);
            version.addField("Supported game version", GetDataClassFromJson.get_data24(), false);
            if (GetDataClassFromJson.get_data22().equals(GetDataClassFromJson.get_data23()))
            {
                version.addField("Latest DOPE & Cli version", GetDataClassFromJson.get_data22(),false);
            }
            else
            {
                version.addField("Latest DOPE version", GetDataClassFromJson.get_data23(),false);
                version.addField("Latest Cli version", GetDataClassFromJson.get_data22(),false);
            }
            version.setFooter(Variables.getWebURL());
            version.setTimestamp(Instant.now());
            channel.sendMessage(version.build()).queue();
        }
        else if (command.equalsIgnoreCase("!help")) {
            EmbedBuilder help = new EmbedBuilder();
            help.setTitle("DOPE | HELP");
            help.setAuthor(author.getName(), null, author.getAvatarUrl());
            help.setColor(Color.red);
            help.setDescription("List of supported commands.");
            help.addField("!help", "Will display list of commands!", false);
            help.addField("!info", "Will display DOPE info!", false);
            help.addField("!status", "Will display the status of the bot!", false);
            help.addField("!version", "Will display DOPE & DO versions!", false);
            help.addField("!invite", "Will display discord invite link!", false);
            help.addField("!link", "Will display website link!", false);
            help.addField("!logs", "Will display path to DOPE logs!", false);
            help.addField("!perkava", "Will display Perkava preview!", false);
            help.addField("!download", "Will display latest bot download links!", false);
            help.addField("!download w", "Will display latest Windows download links!", false);
            help.addField("!download l", "Will display latest Linux download links!", false);
            help.addField("!download w x64", "Will display latest Windows x64 download link!", false);
            help.addField("!download w x86", "Will display latest Windows x86 download link!", false);
            help.addField("!download l x64", "Will display latest Linux x64 download link!", false);
            help.addField("!download l arm", "Will display latest Linux ARM download link!", false);
            help.addField("!roll", "Will roll a number between 1 and 10!", false);
            help.addField("!ping", "Will display ping between You and " + Tag.asMember(Users.getDOPE()) + "!", false);
            help.setFooter(Variables.getWebURL());
            help.setTimestamp(Instant.now());
            channel.sendMessage(help.build()).queue();
        }
        if(!channel.getId().toString().contains(Channels.getBotTalk())) {
            message.delete().queue();
        }
    }

    private void addBotKey(User author, String command, MessageChannel channel, MessageReceivedEvent event) throws IOException {
        String guildID = event.getGuild().getId().toString();
        if (guildID.equals(Channels.getVPSserver())) {
            if (command.contains("!addbot")) {
                String key = command.split("key ")[1].split(" num ")[0];
                int fixNumber = Integer.parseInt(command.split(" num ")[1]);

                File keyFile = new File("DOPEMULTI/" + fixNumber + "/KEY");
                if (keyFile.exists()) {
                    EmbedBuilder log = new EmbedBuilder();
                    log.setTitle("ERROR: KEY file already exists!");
                    log.setAuthor(author.getName(), null, author.getAvatarUrl());
                    log.setColor(Color.red);
                    log.setTimestamp(Instant.now());
                    channel.sendMessage(log.build()).queue();
                } else {
                    byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
                    FilesManager.tryToWriteFile(bytes, "DOPEMULTI/" + fixNumber + "/KEY");
                    Process process = Runtime.getRuntime().exec("DOPEMULTI/" + fixNumber + "/" + fixNumber + "-START.sh");
                    EmbedBuilder log = new EmbedBuilder();
                    log.setTitle("SYNC: New DOPE successfully added!");
                    log.setDescription("KEY: " + key + "\nPATH: DOPEMULTI/" + fixNumber);
                    log.setAuthor(author.getName(), null, author.getAvatarUrl());
                    log.setColor(Color.green);
                    log.setTimestamp(Instant.now());
                    channel.sendMessage(log.build()).queue();
                }
            } else if (command.contains("!removebot")) {
                int fixNumber = Integer.parseInt(command.split(" num ")[1]);

                File keyFile = new File("DOPEMULTI/" + fixNumber + "/KEY");
                if (keyFile.exists()) {
                    FilesManager.tryToDeleteFile("DOPEMULTI/" + fixNumber + "/KEY");
                    Process process = Runtime.getRuntime().exec("DOPEMULTI/" + fixNumber + "/" + fixNumber + "-STOP.sh");
                    EmbedBuilder log = new EmbedBuilder();
                    log.setTitle("SYNC: DOPE KEY successfully removed!");
                    log.setDescription("PATH: DOPEMULTI/" + fixNumber);
                    log.setAuthor(author.getName(), null, author.getAvatarUrl());
                    log.setColor(Color.green);
                    log.setTimestamp(Instant.now());
                    channel.sendMessage(log.build()).queue();
                } else {
                    EmbedBuilder log = new EmbedBuilder();
                    log.setTitle("ERROR: KEY file does not exist in this folder!");
                    log.setDescription("PATH: DOPEMULTI/" + fixNumber);
                    log.setAuthor(author.getName(), null, author.getAvatarUrl());
                    log.setColor(Color.red);
                    log.setTimestamp(Instant.now());
                    channel.sendMessage(log.build()).queue();
                }
            }
        }
    }

    private void addSupport(Message message, User author, String command, MessageChannel channel) throws IOException {
        if (Users.isDevsOrCM(author)) {
            if (command.contains("!addsupport")) {
                List<User> newSup = message.getMentionedUsers();
                Collection<String> data = FilesManager.returnCurrentSupportList();
                newSup.forEach(user -> {
                    String ID = user.getId().toString();
                    if (data.contains(ID)) {
                        EmbedBuilder log = new EmbedBuilder();
                        log.setTitle("ERROR: Support file already exists!");
                        log.setDescription("ID: " + ID + "\nUserName: " + Tag.asMember(ID));
                        log.setAuthor(author.getName(), null, author.getAvatarUrl());
                        log.setColor(Color.red);
                        log.setTimestamp(Instant.now());
                        channel.sendMessage(log.build()).queue();
                    } else {
                        try {
                            FilesManager.createNewUserFile("Support/" + ID);
                            EmbedBuilder log = new EmbedBuilder();
                            log.setTitle("SYNC: New Support successfully added!");
                            log.setDescription("ID: " + ID + "\nUserName: " + Tag.asMember(ID));
                            log.setAuthor(author.getName(), null, author.getAvatarUrl());
                            log.setColor(Color.green);
                            log.setTimestamp(Instant.now());
                            channel.sendMessage(log.build()).queue();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (command.contains("!removesupport")) {
                List<User> removeSup = message.getMentionedUsers();
                Collection<String> data = FilesManager.returnCurrentSupportList();
                removeSup.forEach(user -> {
                    String ID = user.getId().toString();
                    if (data.contains(ID)) {
                        FilesManager.tryToDeleteFile("Support/" + ID + ".txt");
                        EmbedBuilder log = new EmbedBuilder();
                        log.setTitle("SYNC: Support successfully removed!");
                        log.setDescription("ID: " + ID + "\nUserName: " + Tag.asMember(ID));
                        log.setAuthor(author.getName(), null, author.getAvatarUrl());
                        log.setColor(Color.green);
                        log.setTimestamp(Instant.now());
                        channel.sendMessage(log.build()).queue();
                    } else {
                        EmbedBuilder log = new EmbedBuilder();
                        log.setTitle("ERROR: Support file does not exists!");
                        log.setDescription("ID: " + ID + "\nUserName: " + Tag.asMember(ID));
                        log.setAuthor(author.getName(), null, author.getAvatarUrl());
                        log.setColor(Color.red);
                        log.setTimestamp(Instant.now());
                        channel.sendMessage(log.build()).queue();
                    }
                });
            }
        } else if (command.contains("!addsupport") || command.contains("!removesupport")) {
            EmbedBuilder log = new EmbedBuilder();
            log.setTitle("ERROR: Wrong permissions!");
            log.setDescription("Only **Developers** and **CM** can use this command!");
            log.setAuthor(author.getName(), null, author.getAvatarUrl());
            log.setColor(Color.red);
            log.setTimestamp(Instant.now());
            channel.sendMessage(log.build()).queue();
        }
    }

    private void pingDevs(Message message, MessageChannel channel, MessageReceivedEvent event, User author) {
        if (message.getMentionedUsers().toString().contains(Users.getPowerOfDark()) ||
                message.getMentionedUsers().toString().contains(Users.getFrontendDev()) ||
                message.getMentionedRoles().toString().contains(Roles.getDevelopers())) {
            if (!message.getMember().getRoles().toString().contains(Roles.getStaff())) {
                channel.sendMessage(Tag.asMember(message.getAuthor().getId().toString()) + "**, don't tag Developers, please!**").queue();
                event.getGuild().addRoleToMember(message.getMember(), event.getGuild().getRoleById(Roles.getWarned())).queue();

                try {
                    String userName = message.getAuthor().getName().toString();
                    String userID = message.getAuthor().getId().toString();
                    String warnedPath = "Users/" + message.getAuthor().getName().toString();
                    FilesManager.createNewUserFile(warnedPath);
                    FilesManager.writeJson(warnedPath, userName, userID);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
