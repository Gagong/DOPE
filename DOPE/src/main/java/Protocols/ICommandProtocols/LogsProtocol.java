package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import Variables.Variables;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LogsProtocol implements ICommand {
    Variables Variables = new Variables();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        Emote windows = event.getGuild().getEmoteById(Variables.WINDOWS);
        Emote linux = event.getGuild().getEmoteById(Variables.LINUX);
        event.getTextChannel().sendMessage("Select OS by clicking on reaction, where:\n" +
                windows.getAsMention() + " - Windows OS.\n" +
                linux.getAsMention() + " - Linux OS.").queue(message -> {
                message.addReaction(windows).queue();
                message.addReaction(linux).queue();
        });
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("LogsProtocol", "isExecuted", "Logs protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Logs";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
