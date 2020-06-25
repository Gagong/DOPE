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

public class HU implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();
    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Szia, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Új ticketet nyitottál:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Idő: " + formatter.format(date) + "`\n" +
                "`Felhasználó: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Kérlek írj le ide minden információt a problémádról amit csak tudsz, ez nagyban hozzájárul ahhoz, hogy gyorsabban tudjunk segíteni.\n" +
                "> A Support hamarosan jelentkezni fog (Vagy akár meg is jelölhetsz egy Supportert, hátha úgy hamarabb reagálnak).\n" +
                "> Ha lehetséges, csatolj screenshotot, GIF-et vagy DOPE Logot (DOPE Log elérése: `%appdata%\\DOPE\\Logs`).\n\n" +
                "A ticket bezárásához kattints ide :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().putPermissionOverride(event.getMember(), EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_READ,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_EMBED_LINKS,
                Permission.MESSAGE_ATTACH_FILES,
                Permission.MESSAGE_ADD_REACTION), null)
                .setTopic(name)
                .setName(event.getTextChannel().getName() + "-HU")
                .queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("HU_LanguageQuery", "isExecuted", "HU protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Kérlek írj le ide minden információt a problémádról amit csak tudsz, ez nagyban hozzájárul ahhoz, hogy gyorsabban tudjunk segíteni.\n" +
                "> Ha lehetséges, csatolj screenshotot, GIF-et vagy DOPE Logot (DOPE Log elérése: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
