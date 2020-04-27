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
import java.time.Instant;

public class RemoveBotProtocol implements ICommand {
    Channels Channels = new Channels();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        String guildID = event.getGuild().getId();
        if (guildID.equals(Channels.TEST_SERVER)) {
            String pathNumber = args[1];

            File keyFile = new File("DOPEMULTI/" + pathNumber + "/KEY");
            if (keyFile.exists()) {
                try {
                    FilesManager.tryToDeleteFile("DOPEMULTI/" + pathNumber + "/KEY");
                    Process process = Runtime.getRuntime().exec("DOPEMULTI/" + pathNumber + "/" + pathNumber + "-STOP.sh");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EmbedBuilder log = new EmbedBuilder();
                log.setTitle("SYNC: DOPE KEY successfully removed!");
                log.setDescription("PATH: DOPEMULTI/" + pathNumber);
                log.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
                log.setColor(Color.green);
                log.setTimestamp(Instant.now());
                event.getTextChannel().sendMessage(log.build()).queue();
            } else {
                EmbedBuilder log = new EmbedBuilder();
                log.setTitle("ERROR: KEY file does not exist in this folder!");
                log.setDescription("PATH: DOPEMULTI/" + pathNumber);
                log.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
                log.setColor(Color.red);
                log.setTimestamp(Instant.now());
                event.getTextChannel().sendMessage(log.build()).queue();
            }
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("RemoveBotProtocol", "isExecuted", "Remove Bot protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "RemoveBot";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
