package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import Utils.CreateTag;
import Variables.Users;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DOSIDProtocol implements ICommand {
    CreateTag Tag = new CreateTag();
    Users Users = new Users();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("DOPE SID Helper by " + Tag.asMember(Users.Fabio) + "!\n" +
                "Link & Guide: https://github.com/Gagong/DOPE-SID-Login\n" +
                "Video guide: https://youtu.be/u1ZeKGvkcT0").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("DOSIDProtocol", "isExecuted", "DO SID protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "dosid";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
