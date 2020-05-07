package Listeners;

import Protocols.LanguageQueryProtocol;
import Utils.CreateTag;
import Utils.FilesManager;
import Variables.Variables;
import Variables.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.json.simple.parser.ParseException;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class ReactionAdd {
    static Variables Variables = new Variables();
    static Channels Channels = new Channels();
    static CreateTag Tag = new CreateTag();

    public static void onMessageReactionAdd (MessageReactionAddEvent event) throws Exception {
        if (event.getReactionEmote().isEmoji()) {
            if (!event.getUser().isBot() && event.getTextChannel().getName().contains(Variables.CHANNEL_PATTERN))
                LanguageQueryProtocol.HandleLanguageProtocol(event.getReactionEmote().getAsCodepoints(), event);

            if (event.getMessageId().equals(Variables.SUPPORT_EMBED) && event.getReactionEmote().getAsCodepoints().equals(Variables.ENVELOPE))
                CreateTicketChannel(Channels.SUPPORT_CATEGORY, event);

            if (event.getMessageId().equals(Variables.BUG_REPORT_EMBED) && event.getReactionEmote().getAsCodepoints().equals(Variables.ENVELOPE))
                CreateTicketChannel(Channels.BUG_REPORT_CATEGORY, event);

            if (event.getReactionEmote().getAsCodepoints().equals(Variables.LOCK) && !event.getUser().isBot() && event.getTextChannel().getName().contains(Variables.CHANNEL_PATTERN))
                LogTicket(event);
        } else if (event.getReactionEmote().isEmote() && event.getTextChannel().getName().contains(Variables.CHANNEL_PATTERN) && !event.getUser().isBot()) {
            LogsSelector(event);
            event.getReaction().removeReaction(event.getUser()).queue();
        }

    }

    private static void LogsSelector(MessageReactionAddEvent event) {
        if (!event.getUser().isBot()) {
            if (event.getReactionEmote().getEmote().getName().equals("windows")) {
                event.getTextChannel().editMessageById(event.getMessageId(), "Please send logs here.\n" +
                        "We need both folders. You can compress it as **ZIP** or **RAR** archive.\n" +
                        "DOPE Logs path: `%appdata%\\DOPE\\Logs`\n" +
                        "https://cdn.discordapp.com/attachments/598182739228753941/663360155941076992/unknown.png").queue();
            } else if (event.getReactionEmote().getEmote().getName().equals("linux")) {
                event.getTextChannel().editMessageById(event.getMessageId(),"```diff\n" +
                        "+ FOR BEGINNERS (require DOPE reboot)\n" +
                        "For linux logs please use this tool ⇨ #linux-bot-guide\n" +
                        "Make sure you meet the requirements:\n" +
                        "- 1) Install Dependencies and 2) Download/Update DOPE!\n" +
                        "Now you can use:\n" +
                        "- 4)DOPE EXPERIMENTAL ⇨ 2) LOG FOR DOPEX\n\n" +
                        "+ When your issue come back:\n" +
                        "1) Connect back to your VPS (We suggest https://filezilla-project.org/download.php?type=client)\n" +
                        "2) Download the file 'screenlog.0'\n" +
                        "3) Rename file to 'screenlog.txt'\n" +
                        "4) Send it here.```").queue();
            }
        }
    }

    private static void CreateTicketChannel(String category, MessageReactionAddEvent event) throws IOException, ParseException {
        event.getReaction().removeReaction(event.getUser()).queue();

        Integer ID = FilesManager.GetTicketID();
        Member User = event.getMember();

        assert User != null;
        Objects.requireNonNull(event.getGuild().getCategoryById(category)).createTextChannel(Variables.CHANNEL_PATTERN + ID)
                .addPermissionOverride(User,
                        EnumSet.of(Permission.VIEW_CHANNEL,
                                Permission.MESSAGE_READ,
                                Permission.MESSAGE_EMBED_LINKS,
                                Permission.MESSAGE_ATTACH_FILES,
                                Permission.MESSAGE_ADD_REACTION),
                        EnumSet.of(Permission.MESSAGE_WRITE))
                .queue(textChannel ->
                    textChannel.sendMessage("Hello, " + Tag.asMember(User.getId()) + "!\n\n" +
                            "Please select your main language by clicking on reaction!")
                            .queue(message -> {
                                message.addReaction(Variables.FLAG_EN).queue();
                                message.addReaction(Variables.FLAG_RU).queue();
                                message.addReaction(Variables.FLAG_IT).queue();
                                message.addReaction(Variables.FLAG_PT).queue();
                                message.addReaction(Variables.FLAG_HU).queue();
                                message.addReaction(Variables.FLAG_TR).queue();
                                message.addReaction(Variables.FLAG_DE).queue();
                                message.addReaction(Variables.FLAG_ES).queue();
                                message.addReaction(Variables.FLAG_PL).queue();
                                message.addReaction(Variables.FLAG_CZ).queue();
                                message.addReaction(Variables.FLAG_SK).queue();
                                message.addReaction(Variables.FLAG_FR).queue();
                            })
                );
        FilesManager.SetTicketID(ID + 1);
    }

    private static void LogTicket(MessageReactionAddEvent event) {
        event.getTextChannel().delete().queue(e -> {
            EmbedBuilder TicketLog = new EmbedBuilder();
            TicketLog.setTitle("Ticket Closed").addField("```Ticket ID```", event.getTextChannel().getName().split("-")[1], true)
                    .addField("```Closed By```", Tag.asMember(event.getUser().getId()), true)
                    .addField("```Reason```", "Done", true)
                    .setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl())
                    .setColor(Color.green)
                    .setTimestamp(Instant.now());
            Objects.requireNonNull(event.getGuild().getTextChannelById(Channels.TICKETS_ARCHIVE)).sendMessage(TicketLog.build()).queue();
        });
    }
}

