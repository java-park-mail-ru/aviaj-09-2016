package ru.aviaj.database.exception;

public class ExistsException extends Exception{
    public int field; //1-login, 2-email
    public ExistsException(String message, int field) {
        super(message);
        this.field = field;
    }
}
