package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Utils.CreateTag;
import com.dope.gagong.bots.Utils.SQL;
import com.dope.gagong.bots.Variables.Roles;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.awt.*;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WarnedProtocol implements ICommand {
    private final Roles Roles = new Roles();
    private final CreateTag Tag = new CreateTag();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        if (Objects.requireNonNull(event.getMember()).getRoles().toString().contains(Roles.STAFF)) {
            List<User> target = event.getMessage().getMentionedUsers();
            target.forEach(user -> {
                try {
                    SQL.writeWarnedUserInSQL(user.getId(), Instant.now().toString());
                    int count = SQL.getWarnedCountByIDFromSQL(user.getId());
                    SQL.writeUIDIntoWarnedCounterSQL(user.getId(), count);
                    switch (count) {
                        case 1:
                            event.getGuild().addRoleToMember(Objects.requireNonNull(event.getGuild().getMember(user)), Objects.requireNonNull(event.getGuild().getRoleById(Roles.WARNED))).queue();
                            event.getTextChannel().sendMessage("**" + Tag.asMember(user.getId()) + " you have been warned by " + Tag.asMember(event.getAuthor().getId()) + "!**\n" +
                                    "Warning count: " + count + "\n" +
                                    "Next punishment: **Mute for 6 hours!**").queue();
                            return;
                        case 2:
                            event.getGuild().addRoleToMember(Objects.requireNonNull(event.getGuild().getMember(user)), Objects.requireNonNull(event.getGuild().getRoleById(Roles.WARNED))).queue();
                            event.getTextChannel().sendMessage("**" + Tag.asMember(user.getId()) + " you have been warned and muted for 6 hours by " + Tag.asMember(event.getAuthor().getId()) + "!**\n" +
                                    "Warning count: " + count + "\n" +
                                    "Next punishment: **Mute for 24 hours!**").queue();
                            event.getTextChannel().sendMessage("-automute " + user.getId() + " 360").queue();
                            return;
                        case 3:
                            event.getGuild().addRoleToMember(Objects.requireNonNull(event.getGuild().getMember(user)), Objects.requireNonNull(event.getGuild().getRoleById(Roles.WARNED))).queue();
                            event.getTextChannel().sendMessage("**" + Tag.asMember(user.getId()) + " you have been warned and muted for 24 hours by " + Tag.asMember(event.getAuthor().getId()) + "!**\n" +
                                    "Warning count: " + count + "\n" +
                                    "Next punishment: **Kick from server!**").queue();
                            event.getTextChannel().sendMessage("-automute " + user.getId() + " 720").queue();
                            return;
                        case 4:
                            event.getTextChannel().sendMessage("**" + Tag.asMember(user.getId()) + " you have been warned, muted for 3 days and kicked after 4 warnings by " + Tag.asMember(event.getAuthor().getId()) + "!**").queue();
                            event.getTextChannel().sendMessage("-automute " + user.getId() + " 2160").queue();
                            user.openPrivateChannel().queue(privateChannel -> {
                                privateChannel.sendMessage("Hello, " + Tag.asMember(user.getId()) + "!\n" +
                                        "You have been kicked from DOPE server after 4 warnings!'n" +
                                        "**Please follow the communication rules and server rules!**").queue();
                            });
                            try {
                                event.getGuild().kick(Objects.requireNonNull(event.getGuild().getMemberById(user.getId()))).queue();
                            } catch (HierarchyException exception) {
                                event.getTextChannel().sendMessage("Can't modify a member with higher or equal highest role than yourself!").queue();
                            }
                            SQL.writeUIDIntoWarnedCounterSQL(user.getId(), 0);
                            return;
                        default:
                            return;
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        } else {
            EmbedBuilder log = new EmbedBuilder();
            log.setTitle("ERROR: Wrong permissions!");
            log.setDescription("Only **Staff** users can use this command!");
            log.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
            log.setColor(Color.red);
            log.setTimestamp(Instant.now());
            event.getTextChannel().sendMessage(log.build()).queue(e ->
                    e.delete().queueAfter(5, TimeUnit.SECONDS));
        }
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("WarnedProtocol", "isExecuted", "Warned protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "WarnedProtocol";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
