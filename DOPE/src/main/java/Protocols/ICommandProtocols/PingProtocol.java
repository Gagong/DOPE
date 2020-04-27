package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingProtocol implements ICommand {

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        long time = System.currentTimeMillis();
        event.getTextChannel().sendMessage("Pong!")
                .queue(response -> response.editMessageFormat("Pong: %d ms!", System.currentTimeMillis() - time).queue());
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("PingProtocol", "isExecuted", "Ping protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Ping";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
