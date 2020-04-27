package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DetachLicenseProtocol implements ICommand {
    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("To detach a license from an DO Account, press the :x:\n\n" +
                "**Notes:** This action will cost you 24 hours of your license duration.\n" +
                "Doesn't apply to starter & weekend licenses.\n" +
                "**This will not freeze the license!**\n" +
                "https://i.imgur.com/s5FPWKV.png").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("DetachLicenseProtocol", "isExecuted", "Detach license protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
