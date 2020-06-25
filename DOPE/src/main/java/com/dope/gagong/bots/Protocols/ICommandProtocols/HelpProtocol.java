package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.time.Instant;

public class HelpProtocol implements ICommand {
    Variables Variables = new Variables();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        EmbedBuilder help = new EmbedBuilder();
        help.setTitle("DOPE | HELP");
        help.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl());
        help.setColor(Color.red);
        help.setDescription("List of supported commands.");
        help.addField("!hangars", "```How and where setup hangars for Palladium module! ```", false);
        help.addField("!help", "```Will display list of commands!```", false);
        help.addField("!info", "```Will display DOPE info!```", false);
        help.addField("!invite", "```Will display Discord invite link!```", false);
        help.addField("!link", "```Will display Website link!```", false);
        help.addField("!logs", "```How to get & send DOPE logs!```", false);
        help.addField("!love", "```We love DOPE!```", false);
        help.addField("!password", "```How to change DO Account password!```", false);
        help.addField("!perkava", "```Perkava preview & guide!```", false);
        help.addField("!ping", "```Will display ping between You and @DOPE!```", false);
        help.addField("!roll X", "```Will roll a number between 1 and X!```", false);
        help.addField("!squad", "```Where my Squad?```", false);
        help.addField("!status", "```Will display the status of the bot!```", false);
        help.addField("!version", "```Will display current DOPE & DO versions!```", false);
        help.addField("!download", "```Will display latest bot download links!```\n" +
                "Supported arguments to got needed bot version:\n" +
                "`w` - Windows versions.\n" +
                "`l` - Linux versions.\n" +
                "`w x64` - Windows x64 version.\n" +
                "`w x86` - Windows x86(32) version.\n" +
                "`l x64` - Linux x64 version.\n" +
                "`l arm` - Linux ARM version.", false);
        help.setFooter(Variables.WEB_URL);
        help.setTimestamp(Instant.now());
        event.getTextChannel().sendMessage(help.build()).queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("HelpProtocol", "isExecuted", "Help protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Help";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
