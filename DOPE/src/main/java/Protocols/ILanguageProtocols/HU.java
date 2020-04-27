package Protocols.ILanguageProtocols;

import Debug.Debug;
import Interfaces.ILanguage;
import Utils.CreateTag;
import Variables.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        event.getTextChannel().sendMessage("Alô, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Você abriu um novo ticket:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Idő: " + formatter.format(date) + "`\n" +
                "`Felhasználó: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Kérlek írj le ide minden információt a problémádról amit csak tudsz, ez nagyban hozzájárul ahhoz, hogy gyorsabban tudjunk segíteni.\n" +
                "> A Support hamarosan jelentkezni fog (Vagy akár meg is jelölhetsz egy Supportert, hátha úgy hamarabb reagálnak).\n" +
                "> Ha lehetséges, csatolj screenshotot, GIF-et vagy DOPE Logot (DOPE Log elérése: `%appdata%\\DOPE\\Logs`).\n\n" +
                "A ticket bezárásához kattints ide :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().setName(event.getTextChannel().getName() + "-HU").queue();
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
