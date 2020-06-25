package com.dope.gagong.bots;

import com.dope.gagong.bots.Protocols.CommandQueryProtocol;
import com.dope.gagong.bots.Protocols.JDAProtocol;
import com.dope.gagong.bots.Protocols.LanguageQueryProtocol;
import com.dope.gagong.bots.Utils.SQL;
import java.sql.*;

public class Main {
    public static void main (String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
        JDAProtocol.ExecuteJDAProtocol();
        SQL.connectToSQL();
        Thread.sleep(1000);
        CommandQueryProtocol.ExecuteMainProtocol();
        LanguageQueryProtocol.ExecuteLanguageQueryProtocol();
    }
}
