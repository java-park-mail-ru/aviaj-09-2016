package ru.aviaj.model;

/**
 * Created by sibirsky on 25.09.16.
 */
public class Error {

    private int code;
    public static enum ErrorType {
        NoUserId(100),
        NoLogin(10),
        WrongPassword(11),
        DublicateLogin(21),
        DublicateEmail(22),
        EmptyLogin(23),
        EmptyEmail(24),
        EmptyPassword(25),
        UnexpectedError(900);

        private int code;

        ErrorType(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

    }

    public Error(ErrorType errorType) {
        this.code = errorType.getCode();
    }

    public int getCode() {
        return code;
    }

}
