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

public class CZ implements ILanguage {
    CreateTag Tag = new CreateTag();
    Variables Variables = new Variables();

    @Override
    public void Execute(MessageReactionAddEvent event) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(System.currentTimeMillis());
        String name = (Objects.requireNonNull(event.getMember()).getNickname() == null) ? event.getMember().getEffectiveName() : event.getMember().getNickname();

        event.getTextChannel().sendMessage("Ahoj, " + Tag.asMember(event.getMember().getId()) + "!\n\n" +
                "Založil jsi nový ticket:\n" +
                "`ID: " + event.getTextChannel().getName().split(Variables.CHANNEL_PATTERN)[1] + "`\n" +
                "`Čas: " + formatter.format(date) + "`\n" +
                "`Zakladatel: " + name + " | " + event.getMember().getId() + "`\n\n" +
                "> Prosím poskytni nám co nejvíc informaci aby jsme mohli tvůj problem vyřešit co nejdříve.\n" +
                "> Support se svami spojí co nejdříve.(můžete také označit supporta aby se svami spojil a pomohl vám co nejdřív)\n" +
                "> když je to možné přiložte obrázek,GIF nebo DOPE LOGS (DOPE LOGS naleznete `%appdata%\\DOPE\\Logs`).\n\n" +
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
                .setName(event.getTextChannel().getName() + "-CZ")
                .queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReactionAddEvent event) {
        Debug.p("CZ_LanguageQuery", "isExecuted", "CZ protocol: Done!");
    }

    @Override
    public void AskExecute(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> Prosím poskytni nám co nejvíc informaci aby jsme mohli tvůj problem vyřešit co nejdříve.\n" +
                "> když je to možné přiložte obrázek,GIF nebo DOPE LOGS (DOPE LOGS naleznete `%appdata%\\DOPE\\Logs`).").queue();
    }

    @Override
    public String getLanguageName() {
        return null;
    }
}
