package ru.aviaj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.SessionDAO;
import ru.aviaj.database.exception.DbConnectException;

import javax.sql.DataSource;
import java.sql.Connection;


@Service
public class SessionService extends DatabaseService {

    private DataSource ds;

    @Autowired
    public SessionService(DataSource ds) {
        this.ds = ds;
    }

    @Override
    protected Connection getConnection() throws DbConnectException {
        try {
           return DataSourceUtils.getConnection(ds);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DbConnectException("Unable to connect to database!", e);
        }
    }

    public long getUserIdBySession(String session) throws DbConnectException {
        final Connection dbConnection = getConnection();

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.getUserIdBySession(session);
    }

    public boolean addSession(String session, long userId) throws DbConnectException {
        final Connection dbConnection = getConnection();

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.addSession(session, userId);
    }

    public boolean removeSession(String session) throws DbConnectException {
        final Connection dbConnection = getConnection();

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.removeSession(session);
    }

    @SuppressWarnings("unused")
    public boolean removeAll() throws DbConnectException {
        final Connection dbConnection = getConnection();

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.removeAll();
    }

}
