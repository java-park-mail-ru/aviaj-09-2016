package ru.aviaj.database.exception;

import java.sql.SQLException;

public class DbResultSetException extends DbException {
    public DbResultSetException(String message, SQLException e) {
        super(message, e);
    }
}

