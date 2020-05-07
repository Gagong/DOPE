package Protocols.ILanguageProtocols;

import Debug.Debug;
import Interfaces.ILanguage;
import Utils.CreateTag;
import Variables.Variables;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;
import java.util.TimeZone;

public class IT implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Ciao, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Hai aperto un nuovo ticket:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Ora: " + formatter.format(date) + "`\n" +
                "`Creatore: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Forniscici più informazioni possibili cosicché possiamo risolvere velocemente il tuo problema.\n" +
                "> Il supporto sarà presto da te (Puoi anche menzionare qualcuno del support).\n" +
                "> Se possibile aggiungi screenshots, GIF or il log del DOPE (si trova nella cartella: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Per chiudere questo ticket clicca il :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().putPermissionOverride(event.getMember(), EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_READ,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_EMBED_LINKS,
                Permission.MESSAGE_ATTACH_FILES,
                Permission.MESSAGE_ADD_REACTION), null)
                .setTopic(name)
                .setName(event.getTextChannel().getName() + "-IT")
                .queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("IT_LanguageQuery", "isExecuted", "IT protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Forniscici più informazioni possibili cosicché possiamo risolvere velocemente il tuo problema.\n" +
                "> Se possibile aggiungi screenshots, GIF or il log del DOPE (si trova nella cartella: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
