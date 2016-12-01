package ru.aviaj.database.dao;

import ru.aviaj.database.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class SessionDAO {

    private Connection dbConnection;

    public SessionDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public long getUserIdBySession(String session) throws SQLException {
        final Executor executor = new Executor();
        final String query = "SELECT userId FROM Session WHERE session = '" + session + "';";
        return executor.execQuery(dbConnection, query, resultSet -> {
            if (resultSet.next())
                return resultSet.getLong("userId");
            return (long)0;
        });
    }

    public void addSession(String session, long userId) throws SQLException {
        final Executor executor = new Executor();
        final String update = "INSERT INTO Session (session, userId) VALUES ('" + session +
                "', " + Long.toString(userId) + ");";
        executor.execUpdate(dbConnection, update);
    }

    @SuppressWarnings("Duplicates")
    public void removeSession(String session) throws SQLException {
        final Executor executor = new Executor();
        final String update = "DELETE FROM Session WHERE session = '" + session + "';";
        executor.execUpdate(dbConnection, update);
    }

    public void removeAll() throws SQLException {
        final Executor executor = new Executor();
        final String update = "DELETE FROM Session;";
        executor.execUpdate(dbConnection, update);
    }
}
