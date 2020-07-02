package com.dope.gagong.bots.Debug;

import com.dope.gagong.bots.Utils.SQL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Debug {
    public static void p(String packet, String function, String text) {
        System.err.println("[" + packet + "] | <" + function + "> | " + text);
        try {
            if (SQL.connected)
                logSQL(packet, function, text);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void message(String chatType, String event, String guildName, String channelName, String userName, String content) {
        System.out.println("[" + chatType + "] | <" + event + "> | " + guildName + " | " + channelName + " | " + userName + " | " + content);
        try {
            logMessageSQL(chatType, event, guildName, channelName, userName, content);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void b(String packet, String function, boolean bool) {
        System.err.println("[" + packet + "] | <" + function + "> | " + bool);
    }

    public static void d(String packet, String function, Date date) {
        System.err.println("[" + packet + "] | <" + function + "> | " + date);
    }

    private static void logSQL(String p, String f, String d) throws SQLException {
        String time = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());
        SQL.logSQL(time, p, f, d);
    }

    private static void logMessageSQL(String chatType, String event, String guildName, String channelName, String userName, String content) throws SQLException {
        String time = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());
        SQL.logMessageSQL(time, chatType, event, guildName, channelName, userName, content);
    }
}
