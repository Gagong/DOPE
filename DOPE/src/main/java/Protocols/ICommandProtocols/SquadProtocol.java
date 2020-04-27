package Protocols.ICommandProtocols;

import Debug.Debug;
import Utils.CreateTag;
import Interfaces.ICommand;
import Variables.Users;
import Variables.Roles;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SquadProtocol implements ICommand {
    private CreateTag Tag = new CreateTag();
    private Users Users = new Users();
    private Roles Roles = new Roles();

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
        if (Objects.requireNonNull(event.getMessage().getMember()).getRoles().contains(Roles.SQUAD)) {
            StringBuilder squad = new StringBuilder();
            for (String id: TAG) {
                squad.append(id).append(" ");
            }
            event.getTextChannel().sendMessage("WHERE MY SQUAD? " + squad).queue();
        }
        else {
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
