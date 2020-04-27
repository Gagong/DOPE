package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import Variables.Users;
import Variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.Objects;

public class CreateSupportEmbedProtocol implements ICommand {
    Users Users = new Users();
    Variables Variables = new Variables();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        if (Users.isDevsOrCM(event.getAuthor())) {
            EmbedBuilder SupportEmbed = new EmbedBuilder();
            SupportEmbed.setTitle("Open A Ticket!");
            SupportEmbed.setDescription("React with :envelope_with_arrow: to open a ticket! Support will be with You shortly.");
            SupportEmbed.setAuthor("Support Ticket System", null, Objects.requireNonNull(event.getJDA().getUserById(Users.DOPE)).getAvatarUrl());
            SupportEmbed.setColor(Color.green);
            Objects.requireNonNull(event.getGuild().getTextChannelById(Variables.SUPPORT_CHANNEL))
                    .sendMessage(SupportEmbed.build())
                    .complete().addReaction(Variables.ENVELOPE).queue();
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("CreateSupportEmbedProtocol", "isExecuted", "Create Support Embed protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "CreateSupport";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
