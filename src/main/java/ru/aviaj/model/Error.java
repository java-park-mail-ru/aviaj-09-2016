package ru.aviaj.model;

@SuppressWarnings("unused")
public class Error {

    private int code;

    public Error(ErrorType errorType) {
        this.code = errorType.getCode();
    }

    public int getCode() {
        return code;
    }

}
