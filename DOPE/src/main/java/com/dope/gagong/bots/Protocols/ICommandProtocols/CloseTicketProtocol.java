package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Utils.CreateTag;
import com.dope.gagong.bots.Utils.SQL;
import com.dope.gagong.bots.Variables.Variables;
import com.dope.gagong.bots.Variables.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Objects;

public class CloseTicketProtocol implements ICommand {
    Variables Variables = new Variables();
    CreateTag Tag = new CreateTag();
    Channels Channels = new Channels();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        if (event.getTextChannel().getName().contains(Variables.CHANNEL_PATTERN)) {
            EmbedBuilder TicketLog = new EmbedBuilder();
            TicketLog.setTitle("Ticket Closed");
            TicketLog.addField("Ticket ID", event.getTextChannel().getName().split("-")[1], true);
            TicketLog.addField("Closed By", Tag.asMember(event.getAuthor().getId()), true);
            TicketLog.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
            TicketLog.setColor(Color.green);
            TicketLog.setTimestamp(Instant.now());
            Objects.requireNonNull(event.getGuild().getTextChannelById(Channels.TICKETS_ARCHIVE)).sendMessage(TicketLog.build()).queue();
            try {
                SQL.deleteActiveTicketInSQL(event.getTextChannel().getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            event.getTextChannel().delete().queue();
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("CloseTicketProtocol", "isExecuted", "Close Ticket protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Close";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
