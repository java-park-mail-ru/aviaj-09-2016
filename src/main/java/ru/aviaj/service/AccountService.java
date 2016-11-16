package ru.aviaj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.UserProfileDAO;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.model.UserProfile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Service
public class AccountService extends DatabaseService {

    @Autowired
    @Qualifier("accountServiceDs")
    private DataSource ds;

    @Override
    protected Connection getConnection() {
        return DataSourceUtils.getConnection(ds);
    }

    public UserProfile getUserById(long id) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getUserById(id);
    }

    public UserProfile getUserByLogin(String login) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getUserByLogin(login);
    }

    public UserProfile getUserExistance(String login, String email) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getUserExistance(login, email);
    }

    public List<UserProfile> getTopUsers() throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getTopUsers();
    }

    public List<UserProfile> getAllUsers() throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.getUsers();
    }

    public UserProfile addUser(String login, String email, String password) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.addUser(login, email, password);
    }

    public boolean updateUserRating(long userId, int incrementValue) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect database!");
        }

        final UserProfileDAO userDao = new UserProfileDAO(dbConnection);

        return userDao.updateRating(userId, incrementValue);
    }

}
