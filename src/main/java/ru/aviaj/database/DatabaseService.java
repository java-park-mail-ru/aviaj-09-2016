package ru.aviaj.database;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import ru.aviaj.database.exception.DbException;

import javax.sql.DataSource;
import java.sql.Connection;

public abstract class DatabaseService {

    protected DataSource ds;

    protected Connection getConnection() throws DbException {
        try {
            return DataSourceUtils.getConnection(ds);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DbException("Unable to connect to database!", e);
        }
    }

}
