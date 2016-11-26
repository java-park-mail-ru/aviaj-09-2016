package ru.aviaj.database.exception;

public class DbException extends Exception {
    public DbException(String message, Exception cause) {
        super(message, cause);
    }
}
