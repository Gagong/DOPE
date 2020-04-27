package Interfaces;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface ICommand {
    boolean isCalled (String[] args, MessageReceivedEvent event);
    void Protocol(String[] args, MessageReceivedEvent event);
    void isExecuted (boolean success, MessageReceivedEvent event);
    String getCommandName();
    String helpAssist();
}