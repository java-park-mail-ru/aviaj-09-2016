package ru.aviaj.database.exception;

import java.sql.SQLException;

public class DbStatementException extends DbException {
    public DbStatementException(String message, SQLException e) {
        super(message, e);
    }
}
