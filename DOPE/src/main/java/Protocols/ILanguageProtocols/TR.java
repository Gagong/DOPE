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

public class TR implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Merhaba, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Yeni bir bilet açtığınızda:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Açılma Zamanı: " + formatter.format(date) + "`\n" +
                "`Oluşturan: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Sorununuzu daha hızlı çözebilmemiz için lütfen bize mümkün olduğunca fazla bilgi verin.\n" +
                "> Destek kısa süre içinde sizinle olacak (Ayrıca size daha hızlı yardımcı olmak için herhangi bir desteği etiketleyebilirsiniz).\n" +
                "> Mümkünse - ekran görüntüleri, GIF veya DOPE Günlükleri ekleyin (DOPE Günlükleri yolu: `%appdata%\\DOPE\\Logs`).\n\n" +
                "Bu bileti kapatmak için :lock: yazın.")
                .queue(message -> message.addReaction(Variables.LOCK).queue());
        event.getTextChannel().getManager().setName(event.getTextChannel().getName() + "-tr").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("TR_LanguageQuery", "isExecuted", "TR protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Sorununuzu daha hızlı çözebilmemiz için lütfen bize mümkün olduğunca fazla bilgi verin.\n" +
                "> Mümkünse - ekran görüntüleri, GIF veya DOPE Günlükleri ekleyin (DOPE Günlükleri yolu: `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
