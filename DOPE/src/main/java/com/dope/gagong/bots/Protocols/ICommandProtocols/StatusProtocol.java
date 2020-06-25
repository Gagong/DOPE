package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Json.JSONDataParser;
import com.dope.gagong.bots.Interfaces.ICommand;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StatusProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        String v1 = JSONDataParser.GAME_VERSION;
        String v2 = JSONDataParser.GAME_REMOTE;
        if (v1.equals(v2)) {
            event.getTextChannel().sendMessage("Bot status: **Online!**").queue();
            event.getJDA().getPresence().setActivity(Activity.playing("Online!"));
        }
        else {
            event.getTextChannel().sendMessage("Bot Status: **Offline!**").queue();
            event.getJDA().getPresence().setActivity(Activity.playing("Offline!"));
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("StatusProtocol", "isExecuted", "Status protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Status";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
