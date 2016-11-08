package ru.aviaj.database.executor;

import ru.aviaj.database.handler.IResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    public <T> T execQuery(Connection dbConnection, String sqlQuery, IResultHandler<T> resultHandler)
            throws SQLException {

        Statement statement = dbConnection.createStatement();
        statement.execute(sqlQuery);
        final ResultSet resultSet = statement.getResultSet();
        T result = resultHandler.handle(resultSet);
        resultSet.close();
        statement.close();

        return result;
    }

    public void execUpdate(Connection dbConnection, String sqlUpdate)
            throws SQLException {

        Statement statement = dbConnection.createStatement();
        statement.execute(sqlUpdate);
    }
}
