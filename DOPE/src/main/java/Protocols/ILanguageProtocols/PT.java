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

public class PT implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Olá, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Você abriu um novo ticket:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Tempo: " + formatter.format(date) + "`\n" +
                "`Criador: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Forneça o máximo de informações possível para que possamos resolver o seu problema mais rapidamente.\n" +
                "> O suporte estará com você em breve (você também pode faze tag qualquer suporte para ajudá-lo mais rapidamente).\n" +
                "> Se possível - anexe capturas de tela, GIF ou DOPE Logs (registros DOPE: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Para fechar este ticket, click :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().setName(event.getTextChannel().getName() + "-PT").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("PT_LanguageQuery", "isExecuted", "PT protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Forneça o máximo de informações possível para que possamos resolver o seu problema mais rapidamente.\n" +
                "> Se possível - anexe capturas de tela, GIF ou DOPE Logs (registros DOPE: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
