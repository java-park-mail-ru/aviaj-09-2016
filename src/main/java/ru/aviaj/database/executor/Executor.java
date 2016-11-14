package ru.aviaj.database.executor;

import ru.aviaj.database.exception.ConnectException;
import ru.aviaj.database.handler.IResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings({"JDBCResourceOpenedButNotSafelyClosed", "ThrowInsideCatchBlockWhichIgnoresCaughtException"})
public class Executor {
    public <T> T execQuery(Connection dbConnection, String sqlQuery, IResultHandler<T> resultHandler)
            throws SQLException, ConnectException {

        final Statement statement;
        try {
            statement = dbConnection.createStatement();
        }
        catch (SQLException e) {
            throw new ConnectException("Unable to create statement!");
        }

        statement.execute(sqlQuery);
        final ResultSet resultSet = statement.getResultSet();
        final T result = resultHandler.handle(resultSet);
        resultSet.close();
        statement.close();

        return result;
    }

    public void execUpdate(Connection dbConnection, String sqlUpdate)
            throws SQLException, ConnectException {

        final Statement statement;
        try {
            statement = dbConnection.createStatement();
        }
        catch (SQLException e) {
            throw new ConnectException("Unable to create statement!");
        }
        statement.execute(sqlUpdate);
        statement.close();
    }
}
