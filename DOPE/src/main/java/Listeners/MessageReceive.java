package Listeners;

import Debug.Debug;
import Protocols.CommandQueryProtocol;
import Utils.CreateTag;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import Variables.Variables;
import Variables.Channels;
import Variables.Users;
import Variables.Roles;
import java.util.Objects;

public class MessageReceive {
    private static Variables Variables = new Variables();
    private static Channels Channels = new Channels();
    private static Users Users = new Users();
    private static Roles Roles = new Roles();
    private static CreateTag Tag = new CreateTag();


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
                        String userName = message.getAuthor().getName();
                        String userID = message.getAuthor().getId();
                        String warnedPath = "Stuff/DOPE/Users/" + userID;
                        Utils.FilesManager.createNewUserFile(warnedPath);
                        Utils.FilesManager.writeJson(warnedPath, userName, userID);
                    } catch (Exception e) {
                        e.printStackTrace();
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
