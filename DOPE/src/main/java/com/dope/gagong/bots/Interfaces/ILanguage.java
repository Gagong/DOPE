package com.dope.gagong.bots.Interfaces;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public interface ILanguage {
    void Execute (MessageReactionAddEvent event);
    void isExecuted (boolean success, MessageReactionAddEvent event);
    void AskExecute (MessageReceivedEvent event);
    String getLanguageName();
}
