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
            resultSet.next();
            return resultSet.getLong("userId");
        });
    }

    public boolean addSession(String session, long userId) throws SQLException {
        final Executor executor = new Executor();
        final String update = "INSERT INTO Session (session, userId) VALUES ('" + session +
                "', " + Long.toString(userId) + ");";
        executor.execUpdate(dbConnection, update);
        return true;
    }

    @SuppressWarnings("Duplicates")
    public boolean removeSession(String session) throws SQLException {
        final Executor executor = new Executor();
        final String update = "DELETE FROM Session WHERE session = '" + session + "';";
        executor.execUpdate(dbConnection, update);
        return true;
    }

    public boolean removeAll() throws SQLException {
        final Executor executor = new Executor();
        final String update = "DELETE * FROM Session;";
        executor.execUpdate(dbConnection, update);
        return true;
    }
}
