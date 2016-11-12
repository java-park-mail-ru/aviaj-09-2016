package ru.aviaj.database.dao;

import org.springframework.security.access.method.P;
import ru.aviaj.database.executor.Executor;
import ru.aviaj.model.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserProfileDAO {

    private Connection dbConnection;

    public UserProfileDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public UserProfile getUserById(long id) {
        Executor executor = new Executor();
        String query = "SELECT * FROM User WHERE id = " + Long.toString(id) + ";";
        try {
            UserProfile user = executor.execQuery(dbConnection, query, resultSet -> {
                resultSet.next();
                return new UserProfile(resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getLong("id"),
                        resultSet.getLong("rating"));
            });
            return user;
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
        }

        return null;
    }

    public UserProfile getUserByLogin(String login) {
        Executor executor = new Executor();
        String query = "SELECT * FROM User WHERE login = " + login + ";";
        UserProfile user;
        try {
            user = executor.execQuery(dbConnection, query, resultSet -> {
                resultSet.next();
                return new UserProfile(resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getLong("id"),
                        resultSet.getLong("rating"));
            });
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }

        return user;
    }

    public List<UserProfile> getTopUsers() {
        Executor executor = new Executor();
        String query = "SELECT * FROM User ORDER BY rating DESC LIMIT 10;";
        List<UserProfile> userList = new ArrayList<>();
        try {
                Collections.copy(userList, executor.execQuery(dbConnection, query, resultSet -> {
                List<UserProfile> bufferUserList = new ArrayList<>();
                while (resultSet.next()) {
                    bufferUserList.add(new UserProfile(resultSet.getString("login"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getLong("id"),
                            resultSet.getLong("rating")));
                }

                return bufferUserList;
            }));
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }

        return userList;
    }

    public List<UserProfile> getUsers() {
        Executor executor = new Executor();
        String query = "SELECT * FROM User;";
        List<UserProfile> userList = new ArrayList<>();
        try {
            Collections.copy(userList, executor.execQuery(dbConnection, query, resultSet -> {
                List<UserProfile> bufferUserList = new ArrayList<>();
                while (resultSet.next()) {
                    bufferUserList.add(new UserProfile(resultSet.getString("login"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getLong("id"),
                            resultSet.getLong("rating")));
                }

                return bufferUserList;
            }));
        } catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return null;
        }

        return userList;
    }

    /*public int checkUserExistance(String login, String email) {
        Executor executor = new Executor();
        String query = "SELECT login, email FROM User WHERE login='"+login+"' OR emai='"+email+"';";
    } */

    public UserProfile addUser(String login, String email, String password) {
        Executor executor = new Executor();
        String update = "INSERT INTO User(login, email, password) VALUES('" +
                login + "','" + email + "','" + password + "');";
        try {
            executor.execUpdate(dbConnection, update);
        }
        catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return  null;
        }
        UserProfile user = getUserByLogin(login);

        return user;
    }

    public boolean updateRating(long id, long incValue) {
        Executor executor = new Executor();
        String update = "UPDATE User set rating = (rating + " + Long.toString(incValue) + ") WHERE id = " +
                Long.toString(id) + ";";
        try {
            executor.execUpdate(dbConnection, update);
        }
        catch (SQLException e) {
            System.out.println(Integer.toString(e.getErrorCode()) + ": " + e.getSQLState());
            return false;
        }

        return true;
    }
}
