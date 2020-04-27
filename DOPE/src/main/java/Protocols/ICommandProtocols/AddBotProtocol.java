package Protocols.ICommandProtocols;

import Debug.Debug;
import Utils.FilesManager;
import Interfaces.ICommand;
import Variables.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class AddBotProtocol implements ICommand {
    Channels Channels = new Channels();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        String guildID = event.getGuild().getId();
        if (guildID.equals(Channels.TEST_SERVER)) {
            String key = args[1];
            String pathNumber = args[3];
            File keyFile = new File("DOPEMULTI/" + pathNumber + "/KEY");
            if (keyFile.exists()) {
                EmbedBuilder log = new EmbedBuilder();
                log.setTitle("ERROR: KEY file already exists!");
                log.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
                log.setColor(Color.red);
                log.setTimestamp(Instant.now());
                event.getTextChannel().sendMessage(log.build()).queue();
            } else {
                byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
                try {
                    FilesManager.tryToWriteFile(bytes, "DOPEMULTI/" + pathNumber + "/KEY");
                    Process process = Runtime.getRuntime().exec("DOPEMULTI/" + pathNumber + "/" + pathNumber + "-START.sh");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EmbedBuilder log = new EmbedBuilder();
                log.setTitle("SYNC: New DOPE successfully added!");
                log.setDescription("KEY: " + key + "\nPATH: DOPEMULTI/" + pathNumber);
                log.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
                log.setColor(Color.green);
                log.setTimestamp(Instant.now());
                event.getTextChannel().sendMessage(log.build()).queue();
            }
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("AddBotProtocol", "isExecuted", "Add Bot protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "AddBot";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
