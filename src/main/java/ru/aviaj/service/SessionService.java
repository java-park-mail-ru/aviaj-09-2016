package ru.aviaj.service;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.SessionDAO;
import ru.aviaj.database.exception.ConnectException;
import ru.aviaj.model.UserProfile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


@Service
public class SessionService extends DatabaseService {

    private DataSource ds;

    public SessionService(DataSource ds) {
        this.ds = ds;
    }

    @Override
    protected Connection getConnection() {
        return DataSourceUtils.getConnection(ds);
    }

    public long getUserIdBySession(String session) throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect to the database!");
        }

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.getUserIdBySession(session);
    }

    public boolean addSession(String session, long userId) throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect to the database!");
        }

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.addSession(session, userId);
    }

    public boolean removeSession(String session) throws ConnectException {
        final Connection dbConnection = getConnection();
        if (dbConnection == null) {
            throw new ConnectException("Unable to connect to the database!");
        }

        final SessionDAO sessionDao = new SessionDAO(dbConnection);

        return sessionDao.removeSession(session);
    }

}
