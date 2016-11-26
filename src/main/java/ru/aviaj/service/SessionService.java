package ru.aviaj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.SessionDAO;
import ru.aviaj.database.exception.DbException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@Service
public class SessionService extends DatabaseService {

    private DataSource ds;

    @Autowired
    public SessionService(DataSource ds) {
        this.ds = ds;
    }

    @Override
    protected Connection getConnection() throws DbException {
        try {
           return DataSourceUtils.getConnection(ds);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DbException("Unable to connect to database!", e);
        }
    }

    public long getUserIdBySession(String session) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final SessionDAO sessionDao = new SessionDAO(dbConnection);
            return sessionDao.getUserIdBySession(session);
        } catch (SQLException e) {
            throw new DbException("Unable to get user id!", e);
        }
    }

    public void addSession(String session, long userId) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final SessionDAO sessionDao = new SessionDAO(dbConnection);
            sessionDao.addSession(session, userId);
        } catch (SQLException e) {
            throw new DbException("Unable to add session!", e);
        }
    }

    public void removeSession(String session) throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final SessionDAO sessionDao = new SessionDAO(dbConnection);
            sessionDao.removeSession(session);
        } catch (SQLException e) {
            throw new DbException("Unable to remove session!", e);
        }
    }

    @SuppressWarnings("unused")
    public void removeAll() throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final SessionDAO sessionDao = new SessionDAO(dbConnection);
            sessionDao.removeAll();
        } catch (SQLException e) {
            throw new DbException("Unable to remove sessions!", e);
        }
    }

}
