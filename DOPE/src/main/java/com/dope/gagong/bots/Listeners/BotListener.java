package com.dope.gagong.bots.Listeners;

import com.dope.gagong.bots.Protocols.JDAProtocol;
import com.dope.gagong.bots.Utils.SQL;
import com.dope.gagong.bots.Variables.Channels;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
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
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Objects;

public class BotListener extends ListenerAdapter {
    Channels Channels = new Channels();

    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {

    }

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
    public void onMessageReactionRemove (@NotNull MessageReactionRemoveEvent event) {

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
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        try {
            if (!SQL.isUserInWarnedCounterSQL(event.getMember().getId())) {
                SQL.writeUIDIntoWarnedCounterSQL(event.getMember().getId(), 0);
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
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {

    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();
        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.ROLES_LOGS)).sendMessage("```diff\n" +
                "+ Added new role for " + name + " [" + event.getMember().getId() + "]!\n" +
                "+ Role: " + event.getRoles().get(0).getName() +
                "```")
                .queue();
    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();
        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.ROLES_LOGS)).sendMessage("```diff\n" +
                "- Removed role for " + name + " [" + event.getMember().getId() + "]!\n" +
                "- Role: " + event.getRoles().get(0).getName() +
                "```")
                .queue();
    }

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {

    }
}
