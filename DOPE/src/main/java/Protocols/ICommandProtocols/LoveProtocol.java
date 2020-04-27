package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LoveProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(":heart: **We love DOPE** :heart:").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("LoveProtocol", "isExecuted", "Love protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Love";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
