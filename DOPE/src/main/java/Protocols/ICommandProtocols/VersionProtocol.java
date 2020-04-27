package Protocols.ICommandProtocols;

import Debug.Debug;
import Json.JSONDataParser;
import Interfaces.ICommand;
import Variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.time.Instant;

public class VersionProtocol implements ICommand {
    Variables Variables = new Variables();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        EmbedBuilder version = new EmbedBuilder();
        version.setTitle("DOPE | Versions info", Variables.DOWNLOAD_URL);
        version.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
        version.setColor(Color.CYAN);
        version.setDescription("Make sure what You use latest DOPE version!");
        version.addField("Current game version", JSONDataParser.GAME_VERSION, false);
        version.addField("Supported game version", JSONDataParser.GAME_REMOTE, false);
        if (JSONDataParser.DOPE_VERSION.equals(JSONDataParser.CLI_VERSION))
        {
            version.addField("Latest DOPE & Cli version", JSONDataParser.DOPE_VERSION,false);
        }
        else
        {
            version.addField("Latest DOPE version", JSONDataParser.DOPE_VERSION,false);
            version.addField("Latest Cli version", JSONDataParser.CLI_VERSION,false);
        }
        version.setFooter(Variables.WEB_URL);
        version.setTimestamp(Instant.now());
        event.getTextChannel().sendMessage(version.build()).queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("VersionProtocol", "isExecuted", "Version protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Version";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
