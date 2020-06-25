package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PerkavaProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Perkava preview:\n" +
                "If you want to test & see the in-game integration (requires Windows 10), please see the demo:\n" +
                "https://www.youtube.com/watch?v=88BlO3o8PxE").queue();
        //https://discordapp.com/channels/598177730890039298/606417989293572096/608010142230773770
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("PerkavaProtocol", "isExecuted", "Perkava protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Perkava";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
