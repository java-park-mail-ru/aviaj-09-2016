package ru.aviaj.database.exception;

import java.sql.SQLException;

public class DbQueryException extends DbException {
    public DbQueryException(String message, SQLException e) {
        super(message, e);
    }
}
