package com.dope.gagong.bots;

import com.dope.gagong.bots.Protocols.CommandQueryProtocol;
import com.dope.gagong.bots.Protocols.JDAProtocol;
import com.dope.gagong.bots.Protocols.LanguageQueryProtocol;
import com.dope.gagong.bots.Utils.SQL;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main (String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        JDAProtocol.ExecuteJDAProtocol();
        SQL.connectToSQL();
        Thread.sleep(1000);
        CommandQueryProtocol.ExecuteMainProtocol();
        LanguageQueryProtocol.ExecuteLanguageQueryProtocol();
        ProcessBuilder process = new ProcessBuilder();
        process.command("bash", "-c", "python ./Run-DOPE.py");
        Process p = process.start();
    }
}
