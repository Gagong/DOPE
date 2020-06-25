package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Utils.CreateTag;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Variables.Users;
import com.dope.gagong.bots.Variables.Roles;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SquadProtocol implements ICommand {
    private final CreateTag Tag = new CreateTag();
    private final Users Users = new Users();
    private final Roles Roles = new Roles();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        String[] TAG = {
                Tag.asMember(Users.CrankTV),
                Tag.asMember(Users.Kewai),
                Tag.asMember(Users.zhoiak),
                Tag.asMember(Users.AD3RTRON),
                Tag.asMember(Users.Sumi),
                Tag.asMember(Users.era),
                Tag.asMember(Users.Fabio),
                Tag.asMember(Users.Gagong)
        };
        List<Role> UR = Objects.requireNonNull(event.getMember()).getRoles();
        AtomicBoolean state = new AtomicBoolean(false);
        UR.forEach(role -> {
            if (role.getId().equals(Roles.SQUAD))
                state.set(true);
        });
        if (state.get()) {
            StringBuilder squad = new StringBuilder();
            for (String id: TAG) {
                squad.append(id).append(" ");
            }
            event.getTextChannel().sendMessage("WHERE MY SQUAD? " + squad).queue();
        } else {
            EmbedBuilder log = new EmbedBuilder();
            log.setTitle("ERROR: Wrong permissions!");
            log.setDescription("Only **Squad** users can use this command!");
            log.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
            log.setColor(Color.red);
            log.setTimestamp(Instant.now());
            event.getTextChannel().sendMessage(log.build()).queue(e ->
                    e.delete().completeAfter(5, TimeUnit.SECONDS));
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("SquadProtocol", "isExecuted", "Squad protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Squad";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
