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

public class DE implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Hallo, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Du hast ein neues Ticket eröffnet:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Zeit: " + formatter.format(date) + "`\n" +
                "`Ersteller: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Bitte gib uns soviele Informationen wie möglich, damit wir dein Problem so schnell wie möglich lösen können.\n" +
                "> Der Support wird in kürze bei dir sein (Du kannst auch jeden Supporter in deiner Sprache markieren, für möglichst schnellen Support).\n" +
                "> Wenn möglich - Screenshots, GIF oder den DOPE Log (der hier im Windows Pfad zu finden ist: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Um dieses Ticket wieder zu schließen klick auf das :lock: symbol.")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().setName(event.getTextChannel().getName() + "-DE").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("DE_LanguageQuery", "isExecuted", "DE protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Bitte gib uns soviele Informationen wie möglich, damit wir dein Problem so schnell wie möglich lösen können.\n" +
        "> Wenn möglich - Screenshots, GIF oder den DOPE Log (der hier im Windows Pfad zu finden ist: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
