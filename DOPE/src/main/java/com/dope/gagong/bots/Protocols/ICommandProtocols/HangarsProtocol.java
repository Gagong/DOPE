package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HangarsProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Please, setup Hangars if you use Palladium module!\nhttps://ibb.co/WDzq2sb").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("HangarsProtocol", "isExecuted", "Hangars protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Hangars";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
