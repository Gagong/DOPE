package com.dope.gagong.bots.Protocols.ICommandProtocols;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Interfaces.ICommand;
import com.dope.gagong.bots.Variables.Variables;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DownloadProtocol implements ICommand {
    Variables Variables = new Variables();

    @Override
    public boolean isCalled(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void Protocol(String[] args, MessageReceivedEvent event) {
        String text = null;
        switch (args.length) {
            case (0):
                text = "Latest DOPE versions:\n" +
                        "Windows x64 - **" + Variables.WINDOWS_64 + "**\n" +
                        "Windows x86 - **" + Variables.WINDOWS_86 + "**\n" +
                        "Linux x64 - **" + Variables.LINUX_64 + "**\n" +
                        "Linux ARM - **" + Variables.LINUX_ARM + "**\n";
                break;
            case (1):
                if (args[0].equals("w")) {
                    text = "Latest Windows DOPE versions:\n" +
                            "Windows x64 - **" + Variables.WINDOWS_64 + "**\n" +
                            "Windows x86 - **" + Variables.WINDOWS_86 + "**\n";
                } else if (args[0].equals("l")) {
                    text = "Latest Linux DOPE versions:\n" +
                            "Linux x64 - **" + Variables.LINUX_64 + "**\n" +
                            "Linux ARM - **" + Variables.LINUX_ARM + "**\n";
                }
                break;
            case 2:
                if (args[0].equals("w") && args[1].equals("x64")) {
                    text = "Windows x64 - **" + Variables.WINDOWS_64 + "**";
                }
                else if (args[0].equals("w") && args[1].equals("x86")) {
                    text = "Windows x86 - **" + Variables.WINDOWS_86 + "**";
                }
                else if (args[0].equals("l") && args[1].equals("x64")) {
                    text = "Linux x64 - **" + Variables.LINUX_64 + "**";
                }
                else if (args[0].equals("l") && args[1].equals("arm")) {
                    text = "Linux ARM - **" + Variables.LINUX_ARM + "**";
                }
                break;
            default:
                text = "Error while getting URL";
        }
        assert text != null;
        event.getTextChannel().sendMessage(text).queue();
    }

    @Override
    public void isExecuted(boolean success, MessageReceivedEvent event) {
        Debug.p("DownloadProtocol", "isExecuted", "Download protocol: Done!");
    }

    @Override
    public String getCommandName() {
        return "Download";
    }

    @Override
    public String helpAssist() {
        return null;
    }
}
