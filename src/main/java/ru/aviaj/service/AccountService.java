package ru.aviaj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.UserProfileDAO;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.model.UserProfile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class AccountService extends DatabaseService {

    @Autowired
    public AccountService(DataSource ds) {
        this.ds = ds;
    }


    public UserProfile getUserById(long id) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final UserProfileDAO userDao = new UserProfileDAO(dbConnection);
            return userDao.getUserById(id);
        } catch (SQLException e) {
            throw new DbException("Unable to get user!", e);
        }
    }

    public UserProfile getUserByLogin(String login) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final UserProfileDAO userDao = new UserProfileDAO(dbConnection);
            return userDao.getUserByLogin(login);
        } catch (SQLException e) {
            throw new DbException("Unable to get user!", e);
        }
    }

    public UserProfile getUserExistance(String login, String email) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final UserProfileDAO userDao = new UserProfileDAO(dbConnection);
            return userDao.getUserExistance(login, email);
        } catch (SQLException e) {
            throw new DbException("Unable to get user existance!", e);
        }
    }

    public List<UserProfile> getTopUsers() throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final UserProfileDAO userDao = new UserProfileDAO(dbConnection);
            return userDao.getTopUsers();
        } catch (SQLException e) {
            throw new DbException("Unable to get users!", e);
        }
    }

    public List<UserProfile> getAllUsers() throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final UserProfileDAO userDao = new UserProfileDAO(dbConnection);
            return userDao.getUsers();
        } catch (SQLException e) {
            throw new DbException("Unable to get users!", e);
        }
    }

    public UserProfile addUser(String login, String email, String password) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final UserProfileDAO userDao = new UserProfileDAO(dbConnection);
            return userDao.addUser(login, email, password);
        } catch (SQLException e) {
            throw new DbException("Unable to add user!", e);
        }
    }

    public void updateUserRating(long userId, int incrementValue) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final UserProfileDAO userDao = new UserProfileDAO(dbConnection);
            userDao.updateRating(userId, incrementValue);
        } catch (SQLException e) {
            throw new DbException("Unable to update rating!", e);
        }
    }

}
