package com.dope.gagong.bots.Listeners;

import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class ReactionRemove {
    public static void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        //System.out.println("ReactionRemove: " + event.getReactionEmote().getName());
    }
}
