package ru.aviaj.database.exception;

import java.sql.SQLException;

public class DbUpdateException extends DbException {
    public DbUpdateException(String message, SQLException e) {
        super(message, e);
    }
}
