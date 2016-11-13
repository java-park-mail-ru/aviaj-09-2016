package ru.aviaj.database.dao;

import ru.aviaj.database.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class SessionDAO {

    private Connection dbConnection;

    public SessionDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public long getUserIdBySession(String session) {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT userId FROM Session WHERE session = '" + session + "';";
            return executor.execQuery(dbConnection, query, resultSet -> {
                resultSet.next();
                return resultSet.getLong("userId");
            });
        } catch (SQLException e) {
            return 0;
        }
    }

    public boolean addSession(String session, long userId) {
        final Executor executor = new Executor();
        try {
            final String update = "INSERT INTO Session (session, userId) VALUES ('" + session +
                    "', " + Long.toString(userId) + ");";
            executor.execUpdate(dbConnection, update);

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean removeSession(String session) {
        final Executor executor = new Executor();
        try {
            final String update = "DELETE FROM Session WHERE session = '" + session + "';";
            executor.execUpdate(dbConnection, update);

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }
}
