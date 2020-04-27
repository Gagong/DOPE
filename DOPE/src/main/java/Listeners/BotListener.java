package Listeners;

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

public class BotListener extends ListenerAdapter {
    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Listeners.MessageReceive.onMessageReceived(event);
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
        Listeners.ReactionRemove.onMessageReactionRemove(event);
    }

    @Override
    public void onGuildMemberUpdateNickname (@NotNull GuildMemberUpdateNicknameEvent event) {

    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

    }

    @Override
    public void onGuildBan(@NotNull GuildBanEvent event) {

    }

    @Override
    public void onGuildUnban(@NotNull GuildUnbanEvent event) {

    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {

    }

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {

    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {

    }

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {

    }
}
