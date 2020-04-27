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

public class ES implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("¡Hola, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Abres un nuevo boleto:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Hora: " + formatter.format(date) + "`\n" +
                "`Creador: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Proporcione tanta información como sea posible para que podamos resolver su problema más rápido.\n" +
                "> El soporte estará con usted en breve (también puede etiquetar cualquier soporte para ayudarlo más rápido).\n" +
                "> Si es posible, adjunte capturas de pantalla, registros GIF o DOPE (ruta de registros DOPE: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Para cerrar este ticket, haga clic en :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().setName(event.getTextChannel().getName() + "-ES").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("ES_LanguageQuery", "isExecuted", "ES protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Proporcione tanta información como sea posible para que podamos resolver su problema más rápido.\n" +
                "> Si es posible, adjunte capturas de pantalla, registros GIF o DOPE (ruta de registros DOPE: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
