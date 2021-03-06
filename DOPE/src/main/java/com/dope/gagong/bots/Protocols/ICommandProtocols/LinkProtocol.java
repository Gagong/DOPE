package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Variables.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LinkProtocol implements ICommand {
    Variables Variables = new Variables();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Web URL - **" + Variables.WEB_URL + "**").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("LinkProtocol", "isExecuted", "Link protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Link";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
