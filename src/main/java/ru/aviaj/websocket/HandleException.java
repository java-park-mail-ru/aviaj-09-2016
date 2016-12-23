package ru.aviaj.websocket;

public class HandleException extends Exception {

    public HandleException(String message, Exception cause) {
        super(message, cause);
    }

    public HandleException(String message) {
        super(message);
    }
}
