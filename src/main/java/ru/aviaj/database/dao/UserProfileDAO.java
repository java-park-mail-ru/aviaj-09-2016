package ru.aviaj.database.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.database.exception.DbQueryException;
import ru.aviaj.database.exception.DbResultSetException;
import ru.aviaj.database.exception.DbUpdateException;
import ru.aviaj.database.executor.Executor;
import ru.aviaj.database.handler.UserListResultHandler;
import ru.aviaj.database.handler.UserResultHandler;
import ru.aviaj.model.UserProfile;

import java.sql.Connection;
import java.util.List;

@SuppressWarnings({"Duplicates", "unused"})
public class UserProfileDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileDAO.class);

    private Connection dbConnection;

    public UserProfileDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public UserProfile getUserById(long id) throws DbException {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE id = " + Long.toString(id) + ';';
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (DbResultSetException | DbQueryException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    public UserProfile getUserByLogin(String login) throws DbException {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE login = '" + login + "';";
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (DbResultSetException | DbQueryException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    public UserProfile getUserByEmail(String email) throws DbException {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE email = '" + email + "';";
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (DbResultSetException | DbQueryException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    public UserProfile getUserExistance(String login, String email) throws DbException {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE login = '" + login + "' OR email = '" + email +"';";
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (DbResultSetException | DbQueryException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    public List<UserProfile> getTopUsers() throws DbException {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User ORDER BY rating DESC LIMIT 10;";
            return executor.execQuery(dbConnection, query, new UserListResultHandler());
        } catch (DbResultSetException | DbQueryException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    public List<UserProfile> getUsers() throws DbException {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User ORDER BY login;";
            return executor.execQuery(dbConnection, query, new UserListResultHandler());
        } catch (DbResultSetException | DbQueryException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    public UserProfile addUser(String login, String email, String password) throws DbException {
        final Executor executor = new Executor();
        try {
            final String update = "INSERT INTO User(login, email, password) VALUES('" +
                    login + "','" + email + "','" + password + "');";
            executor.execUpdate(dbConnection, update);
        }
        catch (DbUpdateException e) {
            LOGGER.warn(e.getMessage());
            return  null;
        }

        return getUserByLogin(login);
    }

    public boolean updateRating(long id, long incValue) throws DbException {
        final Executor executor = new Executor();
        try {
            final String update = "UPDATE User set rating = (rating + " + Long.toString(incValue) + ") WHERE id = " +
                    Long.toString(id) + ';';
            executor.execUpdate(dbConnection, update);
        }
        catch (DbUpdateException e) {
            LOGGER.warn(e.getMessage());
            return false;
        }

        return true;
    }
}
