package ru.aviaj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import ru.aviaj.database.dao.SessionDAO;
import ru.aviaj.database.exception.DbException;

import javax.sql.DataSource;
import java.sql.Connection;


@Service
public class SessionService {

    @Autowired
    @Qualifier("sessionServiceDs")
    private DataSource ds;

    protected Connection getConnection() {
        return DataSourceUtils.getConnection(ds);
    }

    public long getUserIdBySession(String session) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect to the database!");
        }

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.getUserIdBySession(session);
    }

    public boolean addSession(String session, long userId) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect to the database!");
        }

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.addSession(session, userId);
    }

    public boolean removeSession(String session) throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect to the database!");
        }

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.removeSession(session);
    }

    @SuppressWarnings("unused")
    public boolean removeAll() throws DbException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new DbException("Unable to connect to the database!");
        }

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.removeAll();
    }

}
