package ru.aviaj.database.handler;

import ru.aviaj.model.UserProfile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserListResultHandler implements IResultHandler<List<UserProfile>> {
    @Override
    public List<UserProfile> handle(ResultSet resultSet) throws SQLException {

        final List<UserProfile> bufferUserList = new ArrayList<>();
        while (resultSet.next()) {
            bufferUserList.add(new UserProfile(resultSet.getString("login"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getLong("id"),
                    resultSet.getLong("rating")));
        }

        return bufferUserList;
    }
}
