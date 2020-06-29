package com.dope.gagong.bots.Utils;

import com.dope.gagong.bots.Debug.Debug;
import org.apache.commons.lang3.SystemUtils;

import java.sql.*;

public class SQL {
    private static final String windowsSQLAuth =
            "jdbc:sqlserver://49.12.104.37:1433;"
            + "database=DOPE_TEST;"
            + "user=SA;"
            + "password=OKxzIobOenFXrihKch1RcMFE;"
            + "encrypt=false;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;";
    private static final String linuxSQLAuth = "jdbc:sqlserver://localhost:1433;databaseName=DOPE_TEST";
    private static final String user = "SA";
    private static final String password = "OKxzIobOenFXrihKch1RcMFE";

    public static Connection SQL = null;
    public static Statement statement = null;

    public static boolean connected = false;

    public static void connectToSQL() throws SQLException, ClassNotFoundException {
        SQL = SQLConnection();
        statement = Statement(SQL);
    }

    private static Connection SQLConnection() throws SQLException, ClassNotFoundException {
        Debug.p("SQL", "ConnectToSQL", "Connected to SQL!");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        if (SystemUtils.IS_OS_LINUX) {
            return DriverManager.getConnection(linuxSQLAuth, user, password);
        } else
            return DriverManager.getConnection(windowsSQLAuth);
    }

    private static Statement Statement(Connection connection) throws SQLException {
        Debug.p("SQL", "CreateStatement", "New statement for SQL successfully created!");
        connected = true;
        return connection.createStatement();
    }

    private static ResultSet executeQuery(String query) throws SQLException {
        Debug.p("SQL", "ExecuteQuery", "New query successfully started!\n" +
                "Query params: [" + query.replaceAll("'", "") + "]");
        return statement.executeQuery(query);
    }

    private static void executeUpdate(String query) throws SQLException {
        Debug.p("SQL", "ExecuteUpdate", "New update successfully started!\n" +
                "Update params: [" + query.replaceAll("'", "") + "]");
        statement.executeUpdate(query);
    }

    public static boolean userHaveActiveTicket(String id) throws SQLException {
        Debug.p("SQL", "UserHaveActiveTicket", "UserHaveActiveTicket check successfully started!");
        return executeQuery("SELECT * FROM Tickets WHERE OwnerID = '" + id + "'").next();
    }

    public static String getTicketIDFromSQL(String id) throws SQLException {
        Debug.p("SQL", "GetTicketIDFromSQL", "GetTicketIDFromSQL successfully started!");
        String ticketID = null;
        ResultSet resultSet = executeQuery("SELECT TicketID FROM Tickets WHERE OwnerID = '" + id + "'");
        while (resultSet.next()) {
            ticketID = resultSet.getString("TicketID");
        }
        return ticketID;
    }

    public static void writeActiveTicketInSQL(String channelId, String ticketID, String ownerID) throws SQLException {
        Debug.p("SQL", "WriteActiveTicketInSQL", "WriteActiveTicketInSQL successfully started!");
        if (!userHaveActiveTicket(ownerID))
            executeUpdate("INSERT INTO Tickets VALUES('" + channelId + "', '" + ticketID + "', '" + ownerID + "')");
        else
            Debug.p("SQL", "WriteActiveTicketInSQL", "User [" + ownerID + "] already have active ticket!");
    }

    public static void deleteActiveTicketInSQL(String id) throws SQLException {
        Debug.p("SQL", "DeleteActiveTicketInSQL", "DeleteActiveTicketInSQL successfully started!");
        executeUpdate("DELETE FROM Tickets WHERE TicketID = '"+ id + "'");
    }

    public static void writeWarnedUserInSQL(String id, String warnedTime) throws SQLException {
        Debug.p("SQL", "WriteWarnedUserInSQL", "WriteWarnedUserInSQL successfully started!");
        ResultSet result = executeQuery("SELECT * FROM WarnedUsers WHERE UserID = '" + id + "'");
        if (result.next()) {
            executeUpdate("UPDATE WarnedUsers SET WarnedTime = " + warnedTime + " WHERE UserID = '" + id + "'");
            //executeUpdate("DELETE FROM WarnedUsers WHERE UserID = '" + id + "'");
            //executeUpdate("INSERT INTO WarnedUsers VALUES('" + id + "', '" + warnedTime + "')");
        } else
            executeUpdate("INSERT INTO WarnedUsers VALUES('" + id + "', '" + warnedTime + "')");
    }

    public static ResultSet getWarnedUsersFromSQL() throws SQLException {
        Debug.p("SQL", "GetWarnedUsersFromSQL", "GetWarnedUsersFromSQL successfully started!");
        return executeQuery("SELECT * FROM WarnedUsers");
    }

    public static void deleteWarnedUserFromSQL(String id) throws SQLException {
        Debug.p("SQL", "DeleteWarnedUserFromSQL", "DeleteWarnedUserFromSQL successfully started!");
        executeUpdate("DELETE FROM WarnedUsers WHERE UserID = '"+ id + "'");
    }

    public static boolean getWarnedUserFromSQLByID(String id) throws SQLException {
        return executeQuery("SELECT * FROM WarnedUsers WHERE UserID = '" + id + "'").next();
    }

    public static int getTicketNumberFromSQL() throws SQLException {
        Debug.p("SQL", "GetTicketIDFromSQL", "GetTicketIDFromSQL successfully started!");
        ResultSet result = executeQuery("SELECT * FROM TicketID");
        int ID = 0;
        while (result.next()) {
            ID = result.getInt("ID");
        }
        return ID;
    }

    public static void setTicketNumberInSQL(int id) throws SQLException {
        Debug.p("SQL", "SetTicketIDInSQL", "SetTicketIDInSQL successfully started!");
        //executeUpdate("DELETE FROM TicketID");
        //executeUpdate("INSERT INTO TicketID VALUES('" + id + "')");
        executeUpdate("UPDATE TicketID SET ID = " + id + " WHERE Data = 'ID'");
    }

    public static void setTicketLog(String ownerID, String ticketID, String ticketName, String whoCloseName, String whoCloseID, String reason, String ownerName) throws SQLException {
        Debug.p("SQL", "SetTicketLog", "SetTicketLog successfully started!");
        executeUpdate("INSERT INTO TicketLog VALUES(" +
                "'" + ownerID + "', " +
                "'" + ticketID + "', " +
                "'" + ticketName + "', " +
                "'" + whoCloseName + "', " +
                "'" + whoCloseID + "', " +
                "'" + reason + "', " +
                "'" + ownerName +
                "')");
    }

    public static String getTicketByChannelID(String id) throws SQLException {
        Debug.p("SQL", "GetTicketByChannelID", "GetTicketByChannelID successfully started!");
        String ownerID = "";
        ResultSet result = executeQuery("SELECT * FROM Tickets WHERE TicketID = '" + id + "'");
        while (result.next()) {
            ownerID = result.getString("OwnerID");
        }
        return ownerID;
    }

    public static void writeUIDIntoWarnedCounterSQL(String id, int count) throws SQLException {
        Debug.p("SQL", "WriteUIDIntoWarnedCounterSQL", "WriteUIDIntoWarnedCounterSQL successfully started!");
        executeUpdate("UPDATE WarnedCounter SET WarnCount = " + count + " WHERE UserID = '" + id + "'");
    }

    public static boolean isUserInWarnedCounterSQL(String id) throws SQLException {
        Debug.p("SQL", "IsUserInSQL", "IsUserInSQL successfully started!");
        return executeQuery("SELECT * FROM WarnedCounter WHERE UserID = '" + id + "'").next();
    }

    public static int getWarnedCountByIDFromSQL(String id) throws SQLException {
        Debug.p("SQL", "GetWarnedCountByIDFromSQL", "GetWarnedCountByIDFromSQL successfully started!");
        int count = 0;
        ResultSet result = executeQuery("SELECT * FROM WarnedCounter WHERE UserID = '" + id + "'");
        while (result.next()) {
            count = result.getInt("WarnCount") + 1;
        }
        return count;
    }

    public static void logSQL(String time, String packet, String function, String data) throws SQLException {
        statement.executeUpdate("INSERT INTO Logger VALUES('" + time + "', '" + packet + "', '" + function + "', '" + data +"')");
    }

    public static void logMessageSQL(String time, String chatType, String event, String guildName, String channelName, String userName, String content) throws SQLException {
        statement.executeUpdate("INSERT INTO MessageLogger VALUES('" +
                time + "', '" +
                chatType + "', '" +
                event + "', '" +
                guildName + "', '" +
                channelName + "', '" +
                userName + "', '" +
                content.replaceAll("'", "") +
                "')");
    }

    public static void exceptionLoggerSQL() {

    }
}
