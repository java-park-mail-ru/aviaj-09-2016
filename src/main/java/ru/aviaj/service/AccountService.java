package ru.aviaj.service;

import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.UserProfileDAO;
import ru.aviaj.database.exception.ConnectException;
import ru.aviaj.database.exception.NotExistsException;
import ru.aviaj.model.UserProfile;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AccountService extends DatabaseService {

    protected Connection getConnection() {
        return connectionFactory.getMySQLConnection();
    }

    private Map<String, UserProfile> loginToUser = new HashMap<>();

    public UserProfile addUser(String login, String email, String password) {
        final UserProfile newUserProfile = new UserProfile(login, email, password);
        loginToUser.put(login, newUserProfile);
        return  newUserProfile;
    }

    public Set<Map.Entry<String, UserProfile>> getEntrySet() {
        return loginToUser.entrySet();
    }

    /*public UserProfile addUser(String login, String email, String password) throws ConnectException {

        Connection dbConnection = getConnection();
        if (dbConnection == null)
            throw new ConnectException("Cannot connect to MySQL database!");

        UserProfileDAO userDao = new UserProfileDAO(dbConnection);
        UserProfile user = userDao.addUser(login, email, password);
        if (user == null) {

        }
        return null;
    } */

    public UserProfile getUserById(long id) throws ConnectException, NotExistsException {
        Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect database!");
        }

        UserProfileDAO userDao = new UserProfileDAO(dbConnection);
        UserProfile user = userDao.getUserById(id);
        if (user == null)
            throw new NotExistsException("No such user id!");

        return user;
    }

    public UserProfile getUserByLogin(String login) throws ConnectException, NotExistsException {
        Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect database!");
        }

        UserProfileDAO userDao = new UserProfileDAO(dbConnection);
        UserProfile user = userDao.getUserByLogin(login);
        if (user == null)
            throw new NotExistsException("No such login!");

        return user;
    }
}
