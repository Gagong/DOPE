package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import Variables.Channels;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.io.IOException;

public class StartUpdateProtocol implements ICommand {
    Channels Channels = new Channels();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        String guildID = event.getGuild().getId();
        if (guildID.equals(Channels.TEST_SERVER)) {
            try {
                Process p = Runtime.getRuntime().exec("screen -S xDD -dm ./xUPD.sh");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("StartUpdateProtocol", "isExecuted", "Start Update protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "StartUpdate";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
