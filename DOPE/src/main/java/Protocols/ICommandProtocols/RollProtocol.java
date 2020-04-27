package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.Random;

public class RollProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        Integer number = null;
        if (args.length != 1) return;
        try {
            number = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            event.getTextChannel().sendMessage("Error: Number must be integer!").queue();
            number = 1;
        }
        Random rand = new Random();
        if (number > 0) {
            int roll = rand.nextInt(number) + 1;
            event.getTextChannel().sendMessage("Your roll: " + roll).queue();
        } else event.getTextChannel().sendMessage("Error: Number must be positive!").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("RollProtocol", "isExecuted", "Roll protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Roll";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
