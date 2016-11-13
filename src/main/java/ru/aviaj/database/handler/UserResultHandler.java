package ru.aviaj.database.handler;

import ru.aviaj.model.UserProfile;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultHandler implements IResultHandler<UserProfile> {
    @Override
    public UserProfile handle(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return new UserProfile(resultSet.getString("login"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getLong("id"),
                resultSet.getLong("rating"));
    }
}
