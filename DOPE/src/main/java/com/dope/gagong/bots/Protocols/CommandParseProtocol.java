package com.dope.gagong.bots.Protocols;

import com.dope.gagong.bots.Variables.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandParseProtocol {
    Variables Variables = new Variables();

    public CommandContainer parse (String rw, MessageReceivedEvent event) {
        ArrayList<String> split = new ArrayList<>();
        String raw = rw;
        String beheaded = raw.replaceFirst(Variables.PREFIX, "");
        String[] splitBeheaded = beheaded.split(" ");
        split.addAll(Arrays.asList(splitBeheaded));
        String invoke = split.get(0);
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);

        return new CommandContainer(raw, beheaded, splitBeheaded, invoke, args, event);
    }

    public static class CommandContainer {
        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;
        public final MessageReceivedEvent event;

        public CommandContainer(String raw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent event) {
            this.raw = raw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
        }
    }
}