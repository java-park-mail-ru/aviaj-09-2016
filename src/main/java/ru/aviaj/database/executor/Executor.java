package ru.aviaj.database.executor;

import ru.aviaj.database.handler.IResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings({"JDBCResourceOpenedButNotSafelyClosed", "ThrowInsideCatchBlockWhichIgnoresCaughtException", "DuplicateThrows"})
public class Executor {

    public <T> T execQuery(Connection dbConnection, String sqlQuery, IResultHandler<T> resultHandler)
            throws SQLException {

        try (Statement statement = dbConnection.createStatement()) {

            final boolean execResult = statement.execute(sqlQuery);
            if (!execResult)
                return null;

            try(ResultSet resultSet = statement.getResultSet()) {
                final T result = resultHandler.handle(resultSet);
                resultSet.close();
                statement.close();

                return result;
            }
        }
    }

    public int execUpdate(Connection dbConnection, String sqlUpdate)
            throws SQLException {

        try (Statement statement = dbConnection.createStatement()) {
            final int result = statement.executeUpdate(sqlUpdate);
            statement.close();

            return result;
        }
    }
}
