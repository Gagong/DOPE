package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Json.JSONDataParser;
import com.dope.gagong.bots.Utils.CreateTag;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Variables.Variables;
import com.dope.gagong.bots.Variables.Channels;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InfoProtocol implements ICommand {
    CreateTag Tag = new CreateTag();
    Channels Channels = new Channels();
    Variables Variables = new Variables();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        String v = JSONDataParser.DOPE_VERSION;
        event.getTextChannel().sendMessage("> **DarkOrbit Packet Bot Experiment v." + v + ".**\n" +
                "**Web links**\n" +
                "-> Web URL: **" + Variables.WEB_URL + "**\n" +
                "-> Download: " + Variables.DOWNLOAD_URL + "\n" +
                "-> Bot Panel: " + Variables.BOT_PANEL + "\n" +
                "-> Licenses: " + Variables.LICENSES + "\n\n" +

                "**Discord info**\n" +
                "-> For quick start - read " + Tag.asChannel(Channels.WINDOWS_BOT_GUIDE) + " and "+ Tag.asChannel(Channels.LINUX_BOT_GUIDE) +" channels.\n" +
                "-> To buy license - read " + Tag.asChannel(Channels.PAYMENT_METHODS) + " channel.\n" +
                "-> If you need help - we have support team. Open a new ticket in " + Tag.asChannel(Channels.SUPPORT_CHANNEL) + " channel.\n" +
                "-> If you found a bug - make a report in " + Tag.asChannel(Channels.BUG_REPORT_CHANNEL) + " channel.\n" +
                "-> Check out our profile templates " + Tag.asChannel(Channels.PROFILE_TEMPLATES) + " channel.\n" +
                "-> Giveaways every month! Do not miss your opportunity to participate! " + Tag.asChannel(Channels.GIVEAWAY) + " channel.\n" +
                "-> Check out our staff marketplace in " + Tag.asChannel(Channels.MARKETPLACE) + " channel.").queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("InfoProtocol", "isExecuted", "Info protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Info";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
