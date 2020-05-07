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

public class FR implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Bonjour, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Vous ouvrez un nouveau ticket:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Temps: " + formatter.format(date) + "`\n" +
                "`Créateur: " +  name + " | " + event.getMember().getId() + "`\n\n" +
                "> Veuillez nous fournir autant d'informations que possible afin que nous puissions résoudre votre problème le plus rapidement.\n" +
                "> Le support va bientôt vous répondre (vous pouvez également marquer n'importe quel membre support pour vous aider plus rapidement).\n" +
                "> Si possible - joignez des captures d'écran, des journaux  DOPE (chemin des journaux DOPE: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Pour fermer ce ticket, cliquez sur :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().putPermissionOverride(event.getMember(), EnumSet.of(
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_READ,
                Permission.MESSAGE_WRITE,
                Permission.MESSAGE_EMBED_LINKS,
                Permission.MESSAGE_ATTACH_FILES,
                Permission.MESSAGE_ADD_REACTION), null)
                .setTopic(name)
                .setName(event.getTextChannel().getName() + "-FR")
                .queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("FR_LanguageQuery", "isExecuted", "FR protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Veuillez nous fournir autant d'informations que possible afin que nous puissions résoudre votre problème le plus rapidement.\n" +
                "> Si possible - joignez des captures d'écran, des journaux  DOPE (chemin des journaux DOPE: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
