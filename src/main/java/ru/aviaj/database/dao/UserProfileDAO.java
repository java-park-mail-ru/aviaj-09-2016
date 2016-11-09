package ru.aviaj.database.dao;

import ru.aviaj.database.executor.Executor;
import ru.aviaj.model.UserProfile;

import java.sql.Connection;
import java.sql.SQLException;

public class UserProfileDAO {

    private Connection dbConnection;

    public UserProfileDAO(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public UserProfile getUserById(long id) {
        Executor executor = new Executor();
        String query = "SELECT * FROM User WHERE id = " + Long.toString(id) + ";";
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
            return null;
        }

        return user;
    }
}
