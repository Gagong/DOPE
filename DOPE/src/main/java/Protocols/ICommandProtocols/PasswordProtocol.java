package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PasswordProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("To change **DO Account** password open:\nhttps://imgur.com/a/jKLjhvC").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("PasswordProtocol", "isExecuted", "Password protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Password";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
