package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Protocols.LanguageQueryProtocol;
import com.dope.gagong.bots.Variables.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class AskProtocol implements ICommand {
    Variables Variables = new Variables();
    MessageReactionAddEvent eevent;

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        if (event.getTextChannel().getName().contains(Variables.CHANNEL_PATTERN)) {
            String language = this.langToFlag(event.getTextChannel().getName().split("-")[2]);
            if (LanguageQueryProtocol.LanguageQuery.containsKey(language)) {
                LanguageQueryProtocol.LanguageQuery.get(language).AskExecute(event);
                LanguageQueryProtocol.LanguageQuery.get(language).isExecuted(true, eevent);
            }

        } else event.getTextChannel().sendMessage("This command can be used only in Tickets!").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("AskProtocol", "isExecuted", "Ask protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Ask";
    }

    @Override
    public String helpAssist() {
        return null;
    }

    private String langToFlag (String channelName) {
        switch (channelName) {
            case "cz": return Variables.FLAG_CZ;
            case "de": return Variables.FLAG_DE;
            case "en": return Variables.FLAG_EN;
            case "es": return Variables.FLAG_ES;
            case "fr": return Variables.FLAG_FR;
            case "hu": return Variables.FLAG_HU;
            case "it": return Variables.FLAG_IT;
            case "pl": return Variables.FLAG_PL;
            case "pt": return Variables.FLAG_PT;
            case "ru": return Variables.FLAG_RU;
            case "tr": return Variables.FLAG_TR;
            default: return "U+1f1faU+1f1f8";
        }
    }
}
