package com.dope.gagong.bots;

import com.dope.gagong.bots.Debug.Debug;
import com.dope.gagong.bots.Protocols.CommandQueryProtocol;
import com.dope.gagong.bots.Protocols.JDAProtocol;
import com.dope.gagong.bots.Protocols.LanguageQueryProtocol;
import com.dope.gagong.bots.Utils.SQL;
import org.apache.commons.lang3.SystemUtils;
import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main (String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        JDAProtocol.ExecuteJDAProtocol();
        SQL.connectToSQL();
        Thread.sleep(1000);
        CommandQueryProtocol.ExecuteMainProtocol();
        LanguageQueryProtocol.ExecuteLanguageQueryProtocol();
        if (SystemUtils.IS_OS_LINUX) {
            ProcessBuilder process = new ProcessBuilder();
            process.command("bash", "-c", "python ./Run-DOPE.py");
            Process p = process.start();
        } else Debug.p("Main", "Initialization", "Unsupported OS for run RUNLOG!");
    }
}
