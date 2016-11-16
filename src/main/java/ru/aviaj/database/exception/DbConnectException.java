package ru.aviaj.database.exception;


import org.springframework.jdbc.CannotGetJdbcConnectionException;

public class DbConnectException extends DbException {
    public DbConnectException(String message, CannotGetJdbcConnectionException cause) {
        super(message, cause);
    }
}
