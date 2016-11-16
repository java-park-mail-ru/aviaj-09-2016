package ru.aviaj.database.executor;

import ru.aviaj.database.exception.*;
import ru.aviaj.database.handler.IResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings({"JDBCResourceOpenedButNotSafelyClosed", "ThrowInsideCatchBlockWhichIgnoresCaughtException", "DuplicateThrows"})
public class Executor {

    public <T> T execQuery(Connection dbConnection, String sqlQuery, IResultHandler<T> resultHandler)
            throws DbStatementException, DbResultSetException, DbQueryException {

        try (Statement statement = dbConnection.createStatement()) {

            try {
                statement.execute(sqlQuery);
            } catch (SQLException e) {
                throw new DbQueryException("Unable to execute query statement!", e);
            }

            try (ResultSet resultSet = statement.getResultSet()) {
                final T result = resultHandler.handle(resultSet);

                resultSet.close();
                statement.close();

                return result;
            } catch (SQLException e) {
                throw new DbResultSetException("Unable to process ResultSet!", e);
            }

        }
        catch (SQLException e) {
            throw new DbStatementException("Unable to create statement!",e);
        }
    }

    public void execUpdate(Connection dbConnection, String sqlUpdate)
            throws DbStatementException, DbUpdateException {

        try (Statement statement = dbConnection.createStatement()) {
            try {
                statement.execute(sqlUpdate);
            } catch (SQLException e) {
                throw new DbUpdateException("Unable to execute update query!", e);
            }
            statement.close();
        }
        catch (SQLException e) {
            throw new DbStatementException("Unable to create statement!", e);
        }
    }
}
