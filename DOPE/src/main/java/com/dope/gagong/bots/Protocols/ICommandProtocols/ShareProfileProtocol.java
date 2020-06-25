package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShareProfileProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Click this icon and paste the link here:\n" +
                "https://i.imgur.com/1oKIzZx.png").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("ShareProfileProtocol", "isExecuted", "Share profile protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "share";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
