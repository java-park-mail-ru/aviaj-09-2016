package ru.aviaj.database.dao;

import ru.aviaj.database.executor.Executor;
import ru.aviaj.database.handler.UserListResultHandler;
import ru.aviaj.database.handler.UserResultHandler;
import ru.aviaj.model.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings({"Duplicates", "unused"})
public class UserProfileDAO {

    private Connection dbConnection;

    public UserProfileDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public UserProfile getUserById(long id) throws SQLException {
        final Executor executor = new Executor();
        final String query = "SELECT * FROM User WHERE id = " + Long.toString(id) + ';';
        return executor.execQuery(dbConnection, query, new UserResultHandler());
    }

    public UserProfile getUserByLogin(String login) throws SQLException {
        final Executor executor = new Executor();
        final String query = "SELECT * FROM User WHERE login = '" + login + "';";
        return executor.execQuery(dbConnection, query, new UserResultHandler());
    }

    public UserProfile getUserByEmail(String email) throws SQLException {
        final Executor executor = new Executor();
        final String query = "SELECT * FROM User WHERE email = '" + email + "';";
        return executor.execQuery(dbConnection, query, new UserResultHandler());
    }

    public UserProfile getUserExistance(String login, String email) throws SQLException {
        final Executor executor = new Executor();
        final String query = "SELECT * FROM User WHERE login = '" + login + "' OR email = '" + email +"';";
        return executor.execQuery(dbConnection, query, new UserResultHandler());
    }

    public List<UserProfile> getTopUsers() throws SQLException {
        final Executor executor = new Executor();
        final String query = "SELECT * FROM User ORDER BY rating DESC LIMIT 10;";
        return executor.execQuery(dbConnection, query, new UserListResultHandler());
    }

    public List<UserProfile> getUsers() throws SQLException {
        final Executor executor = new Executor();
        final String query = "SELECT * FROM User ORDER BY login;";
        return executor.execQuery(dbConnection, query, new UserListResultHandler());
    }

    public UserProfile addUser(String login, String email, String password) throws SQLException {
        final Executor executor = new Executor();
        final String update = "INSERT INTO User(login, email, password) VALUES('" +
                login + "','" + email + "','" + password + "');";
        executor.execUpdate(dbConnection, update);

        return getUserByLogin(login);
    }

    public void updateRating(long id, long incValue) throws SQLException {
        final Executor executor = new Executor();
        final String update = "UPDATE User set rating = (rating + " + Long.toString(incValue) + ") WHERE id = " +
                Long.toString(id) + ';';
        executor.execUpdate(dbConnection, update);
    }
}
