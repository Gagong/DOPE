package Protocols;

import Interfaces.ILanguage;
import Protocols.ILanguageProtocols.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import Variables.Variables;
import java.util.HashMap;

public class LanguageQueryProtocol {
    private static Variables Variables = new Variables();
    public static HashMap<String, ILanguage> LanguageQuery = new HashMap<>();

    public static void ExecuteLanguageQueryProtocol() {
        RegisterLanguageProtocol(Variables.FLAG_CZ, new CZ());
        RegisterLanguageProtocol(Variables.FLAG_DE, new DE());
        RegisterLanguageProtocol(Variables.FLAG_EN, new EN());
        RegisterLanguageProtocol(Variables.FLAG_ES, new ES());
        RegisterLanguageProtocol(Variables.FLAG_FR, new FR());
        RegisterLanguageProtocol(Variables.FLAG_HU, new HU());
        RegisterLanguageProtocol(Variables.FLAG_IT, new IT());
        RegisterLanguageProtocol(Variables.FLAG_PL, new PL());
        RegisterLanguageProtocol(Variables.FLAG_PT, new PT());
        RegisterLanguageProtocol(Variables.FLAG_RU, new RU());
        RegisterLanguageProtocol(Variables.FLAG_SK, new CZ());
        RegisterLanguageProtocol(Variables.FLAG_TR, new TR());
    }

    private static void RegisterLanguageProtocol (String ILanguage, ILanguage Protocol) {
        LanguageQuery.put(ILanguage, Protocol);
    }

    public static void HandleLanguageProtocol (String ILanguage, MessageReactionAddEvent event) {
        if (LanguageQuery.containsKey(ILanguage)) {
            event.getTextChannel().deleteMessageById(event.getMessageId()).queue();
            LanguageQuery.get(ILanguage).Execute(event);
            LanguageQuery.get(ILanguage).isExecuted(true, event);
        }
    }
}
