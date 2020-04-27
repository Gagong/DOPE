package Protocols.ICommandProtocols;

import Debug.Debug;
import Interfaces.ICommand;
import Utils.CreateTag;
import Variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class CloseTicketProtocol implements ICommand {
    Variables Variables = new Variables();
    CreateTag Tag = new CreateTag();

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
            Objects.requireNonNull(event.getGuild().getTextChannelById(Variables.TICKETS_ARCHIVE)).sendMessage(TicketLog.build()).queue();
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
