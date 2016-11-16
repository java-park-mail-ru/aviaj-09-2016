package ru.aviaj.database.exception;

import java.sql.SQLException;

public class DbResultSetException extends Exception {
    public DbResultSetException(String message) {
        super(message);
    }
}
