package com.dope.gagong.bots.Protocols.ILanguageProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ILanguage;
import com.dope.gagong.bots.Utils.CreateTag;
import com.dope.gagong.bots.Variables.Variables;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;
import java.util.TimeZone;

public class EN implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Hello, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "You open a new ticket:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Time: " + formatter.format(date) + "`\n" +
                "`Creator: " +  name + " | " + event.getMember().getId() + "`\n\n" +
                "> Please provide us with as much information as possible so that we can solve Your problem faster.\n" +
                "> Support will be with You shortly (You also can tag any support to help You faster).\n" +
                "> If possible - attach **screenshots**, **GIF** or **DOPE Logs** (DOPE Logs path: `%appdata%\\DOPE\\Logs`).\n\n" +
                "To close this ticket click :lock:").queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().putPermissionOverride(event.getMember(), EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_READ,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_EMBED_LINKS,
                Permission.MESSAGE_ATTACH_FILES,
                Permission.MESSAGE_ADD_REACTION), null)
                .setTopic(name)
                .setName(event.getTextChannel().getName() + "-EN")
                .queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("EN_LanguageQuery", "isExecuted", "EN protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Please provide us with as much information as possible so that we can solve Your problem faster.\n" +
                "> If possible - attach **screenshots**, **GIF** or **DOPE Logs** (DOPE Logs path: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
