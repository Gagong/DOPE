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

public class RU implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Добрый день, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Вы открыли новый тикет:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Время: " + formatter.format(date) + "`\n" +
                "`Создатель: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Пожалуйста, предоставьте нам как можно больше информации, чтобы мы могли быстрее решить вашу проблему.\n" +
                "> Наша поддержка свяжется с вами в кратчайшие сроки.\n" +
                "> Если возможно - прикрепите **скриншоты**, **GIF** и **DOPE Logs** (Путь DOPE Logs в проводнике: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Для закрытия тикета нажмите на :lock:")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().setName(event.getTextChannel().getName() + "-RU").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("RU_LanguageQuery", "isExecuted", "RU protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Пожалуйста, предоставьте нам как можно больше информации, чтобы мы могли быстрее решить вашу проблему.\n" +
                "> Если возможно - прикрепите **скриншоты**, **GIF** и **DOPE Logs** (Путь DOPE Logs в проводнике: `%appdata%\\DOPE\\Logs`).\n").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
