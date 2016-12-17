package ru.aviaj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aviaj.database.DatabaseService;
import ru.aviaj.database.dao.SessionDAO;
import ru.aviaj.database.exception.DbException;
import ru.aviaj.messagesystem.Abonent;
import ru.aviaj.messagesystem.Address;
import ru.aviaj.messagesystem.MessageSystem;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@SuppressWarnings({"InfiniteLoopStatement", "Duplicates"})
@Service
public class SessionService extends DatabaseService implements Abonent, Runnable {

    private Address address = new Address();

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

    @Autowired
    public SessionService(DataSource ds) {
        this.ds = ds;
    }

    @Autowired
    MessageSystem messageSystem;

    @Override
    public Address getAddress() {
        return this.address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public void run() {
        while(true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.error("Unable to sleep thread!", e);
            }
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

    public void truncateAll() throws DbException {
        final Connection dbConnection = getConnection();
        try {
            final SessionDAO sessionDAO = new SessionDAO(dbConnection);
            sessionDAO.truncate();
        } catch (SQLException e) {
            throw new DbException("Unable to truncate table Session!", e);
        }
    }

}
