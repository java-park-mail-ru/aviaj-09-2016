package ru.aviaj.database.executor;

import ru.aviaj.database.handler.IResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    public <T> T execQuery(Connection dbConnection, String sqlQuery, IResultHandler<T> resultHandler)
            throws SQLException {

        final Statement statement = dbConnection.createStatement();
        statement.execute(sqlQuery);
        final ResultSet resultSet = statement.getResultSet();
        final T result = resultHandler.handle(resultSet);
        resultSet.close();
        statement.close();

        return result;
    }

    public void execUpdate(Connection dbConnection, String sqlUpdate)
            throws SQLException {

        final Statement statement = dbConnection.createStatement();
        statement.execute(sqlUpdate);
        statement.close();
    }
}
