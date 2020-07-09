package com.dope.gagong.bots.Listeners;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Protocols.JDAProtocol;
import com.dope.gagong.bots.Utils.SQL;
import com.dope.gagong.bots.Variables.Channels;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.sql.SQLException;
import java.util.Objects;

public class BotListener extends ListenerAdapter {
    Channels Channels = new Channels();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        MessageReceive.onMessageReceived(event);
    }

    @Override
    public void onMessageReactionAdd (@NotNull MessageReactionAddEvent event) {
        try {
            ReactionAdd.onMessageReactionAdd(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuildMemberUpdateNickname (@NotNull GuildMemberUpdateNicknameEvent event) {
        String old = event.getOldNickname() == null ? event.getMember().getNickname() : event.getOldNickname();
        String newn = event.getNewNickname() == null ? event.getMember().getEffectiveName() : event.getNewNickname();
        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.NICKNAMES_LOGS)).sendMessage("```diff\n" +
                "+ User update nickname from " + old + " to " + newn + "!\n" +
                "```")
                .queue();
    }

    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        try {
            SQL.statement.executeUpdate("INSERT INTO Roles VALUES('" + event.getRole().getName() + "', '" + event.getRole().getId() + "')");
            SQL.statement.executeUpdate("ALTER TABLE UserRoles ADD [" + event.getRole().getName() + "] bit");
            Debug.p("BotListener", "onRoleCreate", "Role " + event.getRole().getName() + "|" + event.getRole().getId() + " successfully created!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        try {
            SQL.statement.executeUpdate("DELETE FROM Roles WHERE RoleName = '" + event.getRole().getName() + "'");
            //SQL.statement.executeUpdate("ALTER TABLE UserRoles DROP COLUMN " + event.getRole().getName());
            JDAProtocol.JDA.getTextChannelById(Channels.STAFF).sendMessage("<@140422565393858560> Role delete event: [" + event.getRole().getName() + "]\n" +
                    "You need to change it in DB -> UserRoles, because ALTER TABLE broken.\n" +
                    "Queue:\n" +
                    "```SQL\n" +
                    "ALTER TABLE UserRoles DROP COLUMN " + event.getRole().getName() + "\n" +
                    "GO\n" +
                    "```").queue();
            Debug.p("BotListener", "onRoleDelete", "Role " + event.getRole().getName() + "|" + event.getRole().getId() + " successfully deleted!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onRoleUpdateName(@NotNull RoleUpdateNameEvent event) {
        try {
            SQL.statement.executeUpdate("UPDATE Roles SET RoleName = '[" + event.getNewName() + "]' WHERE RoleID = '" + event.getRole().getId() +"'");
            JDAProtocol.JDA.getTextChannelById(Channels.STAFF).sendMessage("<@140422565393858560> Role name update: [" + event.getOldName() + " to " + event.getNewName() + "]\n" +
                    "You need to change it in DB -> UserRoles, because ALTER TABLE broken.\n" +
                    "Queue:\n" +
                    "```SQL\n" +
                    "ALTER TABLE UserRoles CHANGE '" + event.getOldName() + "' '" + event.getNewName() + "' bit\n" +
                    "GO\n" +
                    "```").queue();
            //SQL.statement.executeUpdate("ALTER TABLE UserRoles CHANGE '[" + event.getOldName() + "]' '[" + event.getNewName() + "]' bit");
            Debug.p("BotListener", "onRoleUpdateName", "Role name update: [" + event.getOldName() + " to " + event.getNewName() + "]");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        try {
            if (!SQL.isUserInWarnedCounterSQL(event.getMember().getId())) {
                SQL.writeUIDIntoWarnedCounterSQL(event.getMember().getId(), 0);
                SQL.statement.executeUpdate("INSERT INTO UserRoles(UserID) VALUES(" + event.getMember().getId() + ")");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onGuildBan(@NotNull GuildBanEvent event) {
        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.BAN_LOGS)).sendMessage("```diff\n" +
                "- User " + event.getUser().getName() + " was banned!\n" +
                "```")
                .queue();
    }

    @Override
    public void onGuildUnban(@NotNull GuildUnbanEvent event) {
        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.BAN_LOGS)).sendMessage("```diff\n" +
                "+ User " + event.getUser().getName() + " was unbanned!\n" +
                "```")
                .queue();
    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();
        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.ROLES_LOGS)).sendMessage("```diff\n" +
                "+ Added new role for " + name + " [" + event.getMember().getId() + "]!\n" +
                "+ Role: " + event.getRoles().get(0).getName() +
                "```")
                .queue();
        try {
            SQL.statement.executeUpdate("UPDATE UserRoles SET " + event.getRoles().get(0).getName() + " = 1 WHERE UserID = '" + event.getMember().getId() + "'");
            Debug.p("BotListener", "onGuildMemberRoleAdd", "New role (" + event.getRoles().get(0).getName() + ") for " + name + " [" + event.getMember().getId() + "] successfully added!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();
        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.ROLES_LOGS)).sendMessage("```diff\n" +
                "- Removed role for " + name + " [" + event.getMember().getId() + "]!\n" +
                "- Role: " + event.getRoles().get(0).getName() +
                "```")
                .queue();
        try {
            SQL.statement.executeUpdate("UPDATE UserRoles SET " + event.getRoles().get(0).getName() + " = 0 WHERE UserID = '" + event.getMember().getId() + "'");
            Debug.p("BotListener", "onGuildMemberRoleRemove", "New role (" + event.getRoles().get(0).getName() + ") for " + name + " [" + event.getMember().getId() + "] successfully removed!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
