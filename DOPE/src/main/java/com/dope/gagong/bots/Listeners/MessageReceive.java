package com.dope.gagong.bots.Listeners;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Protocols.CommandQueryProtocol;
import com.dope.gagong.bots.Utils.CreateTag;
import com.dope.gagong.bots.Utils.SQL;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import com.dope.gagong.bots.Variables.Variables;
import com.dope.gagong.bots.Variables.Channels;
import com.dope.gagong.bots.Variables.Users;
import com.dope.gagong.bots.Variables.Roles;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Objects;

public class MessageReceive {
    private static final Variables Variables = new Variables();
    private static final Channels Channels = new Channels();
    private static final Users Users = new Users();
    private static final Roles Roles = new Roles();
    private static final CreateTag Tag = new CreateTag();


    public static void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        boolean bot = author.isBot();

        if (event.getMessage().getContentDisplay().startsWith(Variables.PREFIX)) {
            CommandQueryProtocol.HandleCommandProtocol(CommandQueryProtocol.parser.parse(event.getMessage().getContentDisplay().toLowerCase(), event));
            if (!event.getTextChannel().getId().equals(Channels.TALK_WITH_BOT))
                event.getMessage().delete().queue();
        }

        if (event.isFromType(ChannelType.TEXT) && !bot)
        {
            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();
            Member member = event.getMember();
            String name;
            if (message.isWebhookMessage())
            {
                name = author.getName();
            }
            else
            {
                assert member != null;
                name = member.getEffectiveName();
            }
            if (message.getMentionedUsers().toString().contains(Users.PowerOfDark) ||
                    message.getMentionedUsers().toString().contains(Users.FrontendDev) ||
                    message.getMentionedRoles().toString().contains(Roles.DEVELOPERS)) {
                if (!Objects.requireNonNull(message.getMember()).getRoles().toString().contains(Roles.STAFF)) {
                    event.getTextChannel().sendMessage(Tag.asMember(message.getAuthor().getId()) + "**, don't tag Developers, please!**").queue();
                    event.getGuild().addRoleToMember(message.getMember(), Objects.requireNonNull(event.getGuild().getRoleById(Roles.WARNED))).queue();
                    try {
                        SQL.writeWarnedUserInSQL(message.getAuthor().getId(), Instant.now().toString());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }

            String createChatString = guild.getName() + " | " + textChannel.getName() + " | " + name + " | " + msg;
            Debug.message("GUILD CHAT", "MessageReceive", createChatString);
        }
        else if (event.isFromType(ChannelType.PRIVATE) && !bot)
        {
            PrivateChannel privateChannel = event.getPrivateChannel();
            String createChatString = author.getName() + " | " + msg;
            Debug.message("PRIVATE CHAT", "MessageReceive", createChatString);
        }
    }
}
