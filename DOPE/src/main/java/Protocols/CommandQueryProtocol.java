package Protocols;

import Interfaces.ICommand;
import Protocols.ICommandProtocols.*;
import Utils.Api;

import java.util.HashMap;

public class CommandQueryProtocol {
    public static final CommandParseProtocol parser = new CommandParseProtocol();
    public static HashMap<String, ICommand> commands = new HashMap<>();

    public static void ExecuteMainProtocol() {
        RegisterCommandProtocol("detach", new DetachLicenseProtocol());
        RegisterCommandProtocol("dosid", new DOSIDProtocol());
        RegisterCommandProtocol("download", new DownloadProtocol());
        RegisterCommandProtocol("hangars", new HangarsProtocol());
        RegisterCommandProtocol("help", new HelpProtocol());
        RegisterCommandProtocol("info", new InfoProtocol());
        RegisterCommandProtocol("invite", new InviteProtocol());
        RegisterCommandProtocol("link", new LinkProtocol());
        RegisterCommandProtocol("logs", new LogsProtocol());
        RegisterCommandProtocol("love", new LoveProtocol());
        RegisterCommandProtocol("password", new PasswordProtocol());
        RegisterCommandProtocol("perkava", new PerkavaProtocol());
        RegisterCommandProtocol("ping", new PingProtocol());
        RegisterCommandProtocol("roll", new RollProtocol());
        RegisterCommandProtocol("share", new ShareProfileProtocol());
        RegisterCommandProtocol("squad", new SquadProtocol());
        RegisterCommandProtocol("status", new StatusProtocol());
        RegisterCommandProtocol("version", new VersionProtocol());

        RegisterCommandProtocol("startupdate", new StartUpdateProtocol());
        RegisterCommandProtocol("addbot", new AddBotProtocol());
        RegisterCommandProtocol("removebot", new RemoveBotProtocol());

        RegisterCommandProtocol("support", new CreateSupportEmbedProtocol());
        RegisterCommandProtocol("bugreport", new CreateBugReportEmbedProtocol());
        RegisterCommandProtocol("ask", new AskProtocol());
        RegisterCommandProtocol("close", new CloseTicketProtocol());
    }

    private static void RegisterCommandProtocol (String command, ICommand c) {
        commands.put(command, c);
    }

    public static void HandleCommandProtocol (CommandParseProtocol.CommandContainer command) {
        if (commands.containsKey(command.invoke)) {
            Api.Update();
            commands.get(command.invoke).Protocol(command.args, command.event);
            commands.get(command.invoke).isExecuted(true, command.event);
        }
    }
}
