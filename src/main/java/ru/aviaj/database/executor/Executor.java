package ru.aviaj.database.executor;

import ru.aviaj.database.exception.DbException;
import ru.aviaj.database.exception.DbQueryException;
import ru.aviaj.database.exception.DbResultSetException;
import ru.aviaj.database.exception.DbUpdateException;
import ru.aviaj.database.handler.IResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings({"JDBCResourceOpenedButNotSafelyClosed", "ThrowInsideCatchBlockWhichIgnoresCaughtException"})
public class Executor {

    public <T> T execQuery(Connection dbConnection, String sqlQuery, IResultHandler<T> resultHandler)
            throws DbException, DbResultSetException, DbQueryException {

        try (Statement statement = dbConnection.createStatement()) {

            try {
                statement.execute(sqlQuery);
            } catch (SQLException e) {
                throw new DbQueryException(e.toString());
            }

            try (ResultSet resultSet = statement.getResultSet()) {
                final T result = resultHandler.handle(resultSet);

                resultSet.close();
                statement.close();

                return result;
            } catch (SQLException e) {
                throw new DbResultSetException(e.toString());
            }

        }
        catch (SQLException e) {
            throw new DbException("Unable to create statement!" + e.toString());
        }
    }

    public void execUpdate(Connection dbConnection, String sqlUpdate)
            throws DbException, DbUpdateException {

        try (Statement statement = dbConnection.createStatement()) {
            try {
                statement.execute(sqlUpdate);
            } catch (SQLException e) {
                throw new DbUpdateException(e.toString());
            }
            statement.close();
        }
        catch (SQLException e) {
            throw new DbException("Unable to create statement: " + e.toString());
        }
    }
}
