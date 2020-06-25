package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Variables.Variables;
import com.dope.gagong.bots.Variables.Users;
import com.dope.gagong.bots.Variables.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.Objects;

public class CreateBugReportEmbedProtocol implements ICommand {
    Variables Variables = new Variables();
    Channels Channels = new Channels();
    Users Users = new Users();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        if (Users.isDevsOrCM(event.getAuthor())) {
            EmbedBuilder SupportEmbed = new EmbedBuilder();
            SupportEmbed.setTitle("Open A Bug Report!");
            SupportEmbed.setDescription("React with :envelope_with_arrow: to open a bug report! Our team will be with You shortly.");
            SupportEmbed.setAuthor("Bug Report Ticket System", null, Objects.requireNonNull(event.getJDA().getUserById(Users.DOPE)).getAvatarUrl());
            SupportEmbed.setColor(Color.green);
            Objects.requireNonNull(event.getGuild().getTextChannelById(Channels.BUG_REPORT_CHANNEL))
                    .sendMessage(SupportEmbed.build())
                    .complete().addReaction(Variables.ENVELOPE).queue();
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("CreateBugReportEmbedProtocol", "isExecuted", "Create BugReport Embed protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "BugReportTicket";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
