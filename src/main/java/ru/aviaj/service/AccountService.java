package ru.aviaj.service;

import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.UserProfileDAO;
import ru.aviaj.database.exception.ConnectException;
import ru.aviaj.database.exception.NotExistsException;
import ru.aviaj.model.UserProfile;

import java.sql.Connection;
import java.util.List;

@Service
public class AccountService extends DatabaseService {

    @Override
    protected Connection getConnection() {
        return connectionFactory.getMySQLConnection();
    }

    public UserProfile getUserById(long id) throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getUserById(id);
    }

    public UserProfile getUserByLogin(String login) throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getUserByLogin(login);
    }

    public List<UserProfile> getTopUsers() throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getTopUsers();
    }

    public List<UserProfile> getAllUsers() throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getUsers();
    }

    public UserProfile addUser(String login, String email, String password) throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.addUser(login, email, password);
    }

}
