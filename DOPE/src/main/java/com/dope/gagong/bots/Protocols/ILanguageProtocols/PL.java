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

public class PL implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Witaj, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Otwierasz nowe zapytanie:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Czas: " + formatter.format(date) + "`\n" +
                "`Twórca: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Podaj nam jak najwięcej informacji, abyśmy mogli szybciej rozwiązać twój problem.\n" +
                "> Wsparcie techniczne niedługo się do ciebie odezwie (możesz również oznaczyć dowolnego członka ekipy, aby pomógł Ci szybciej).\n" +
                "> Jeśli to możliwe - dołącz zrzuty ekranu, pliki GIF lub logi DOPE (ścieżka DOPE Logs: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Aby zamknąć ten bilet, kliknij :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().putPermissionOverride(event.getMember(), EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_READ,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_EMBED_LINKS,
                Permission.MESSAGE_ATTACH_FILES,
                Permission.MESSAGE_ADD_REACTION), null)
                .setTopic(name)
                .setName(event.getTextChannel().getName() + "-PL")
                .queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("PL_LanguageQuery", "isExecuted", "PL protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Podaj nam jak najwięcej informacji, abyśmy mogli szybciej rozwiązać twój problem.\n" +
                "> Jeśli to możliwe - dołącz zrzuty ekranu, pliki GIF lub logi DOPE (ścieżka DOPE Logs: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
