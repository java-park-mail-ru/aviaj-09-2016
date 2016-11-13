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

    public UserProfile getUserById(long id) {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE id = " + Long.toString(id) + ';';
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }
    }

    public UserProfile getUserByLogin(String login) {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE login = '" + login + "';";
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }
    }

    public UserProfile getUserByEmail(String email) {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE email = '" + email + "';";
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }
    }

    public UserProfile getUserExistance(String login, String email) {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User WHERE login = '" + login + "' OR email = '" + email +"';";
            return executor.execQuery(dbConnection, query, new UserResultHandler());
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }
    }

    public List<UserProfile> getTopUsers() {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User ORDER BY rating DESC LIMIT 10;";
            return executor.execQuery(dbConnection, query, new UserListResultHandler());
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }
    }

    public List<UserProfile> getUsers() {
        final Executor executor = new Executor();
        try {
            final String query = "SELECT * FROM User ORDER BY login;";
            return executor.execQuery(dbConnection, query, new UserListResultHandler());
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }
    }

    public UserProfile addUser(String login, String email, String password) {
        final Executor executor = new Executor();
        try {
            final String update = "INSERT INTO User(login, email, password) VALUES('" +
                    login + "','" + email + "','" + password + "');";
            executor.execUpdate(dbConnection, update);
        }
        catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return  null;
        }

        return getUserByLogin(login);
    }

    public boolean updateRating(long id, long incValue) {
        final Executor executor = new Executor();
        try {
            final String update = "UPDATE User set rating = (rating + " + Long.toString(incValue) + ") WHERE id = " +
                    Long.toString(id) + ';';
            executor.execUpdate(dbConnection, update);
        }
        catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return false;
        }

        return true;
    }
}
