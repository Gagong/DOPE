package com.dope.gagong.bots.Utils;

import com.dope.gagong.bots.Debug.Debug;
import java.sql.*;

public class SQL {
    private static final String sqlServer =
            "jdbc:sqlserver://49.12.104.37:1433;"
            + "database=DOPE_TEST;"
            + "user=SA;"
            + "password=OKxzIobOenFXrihKch1RcMFE;"
            + "encrypt=false;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;";

    public static Connection SQL = null;
    public static Statement statement = null;

    public static void connectToSQL() throws SQLException, ClassNotFoundException {
        SQL = SQLConnection();
        statement = Statement(SQL);
    }

    private static Connection SQLConnection() throws SQLException, ClassNotFoundException {
        Debug.p("SQL", "ConnectToSQL", "Connected to SQL!");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=DOPE_TEST";
        return DriverManager.getConnection(url, "SA", "OKxzIobOenFXrihKch1RcMFE");
        //return DriverManager.getConnection(sqlServer);
    }

    private static Statement Statement(Connection connection) throws SQLException {
        Debug.p("SQL", "CreateStatement", "New statement for SQL successfully created!");
        return connection.createStatement();
    }

    private static ResultSet executeQuery(String query) throws SQLException {
        Debug.p("SQL", "ExecuteQuery", "New query successfully started!\n" +
                "Query params: [" + query +"]");
        return statement.executeQuery(query);
    }

    private static void executeUpdate(String query) throws SQLException {
        Debug.p("SQL", "ExecuteUpdate", "New update successfully started!\n" +
                "Update params: [" + query + "]");
        statement.executeUpdate(query);
    }

    public static boolean userHaveActiveTicket(String id) throws SQLException {
        Debug.p("SQL", "UserHaveActiveTicket", "UserHaveActiveTicket check successfully started!");
        ResultSet result = executeQuery("SELECT * FROM Tickets WHERE OwnerID = '" + id + "'");
        return result.next();
    }

    public static ResultSet getTicketIDFromSQL(String id) throws SQLException {
        Debug.p("SQL", "GetTicketIDFromSQL", "GetTicketIDFromSQL successfully started!");
        return executeQuery("SELECT TicketID FROM Tickets WHERE OwnerID = '" + id + "'");
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
            executeUpdate("DELETE FROM WarnedUsers WHERE UserID = '" + id + "'");
            executeUpdate("INSERT INTO WarnedUsers VALUES('" + id + "', '" + warnedTime + "')");
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
            ID = result.getInt("TicketID");
        }
        return ID;
    }

    public static void setTicketNumberInSQL(int id) throws SQLException {
        Debug.p("SQL", "SetTicketIDInSQL", "SetTicketIDInSQL successfully started!");
        executeUpdate("DELETE FROM TicketID");
        executeUpdate("INSERT INTO TicketID VALUES('" + id + "')");
    }
}
