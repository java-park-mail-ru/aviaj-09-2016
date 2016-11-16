package ru.aviaj.service;

import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.UserProfileDAO;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.model.UserProfile;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class AccountService extends DatabaseService {

    @Override
    protected Connection getConnection() {

        final Map<String, String> envVar = System.getenv();
        final String mysqlUser = envVar.get("AVIAJ_MYSQL_USER");
        final String mysqlPassword = envVar.get("AVIAJ_MYSQL_PASSWORD");
        final String mysqlPath = envVar.get("AVIAJ_MYSQL_PATH");

        try {
            final Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
            final String url = "jdbc:" +
                     mysqlPath + "?user=" + mysqlUser + "&password=" + mysqlPassword + "&reconnect=true";

            return DriverManager.getConnection(url);
        }
        catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
