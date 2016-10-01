package ru.aviaj.model;

/**
 * Created by sibirsky on 25.09.16.
 */
public class Error {

    private int code;
    public static enum ErrorType {
        NOUSERID(100),
        ALREADYLOGIN(101),
        NOTLOGINED(102),
        NOLOGIN(10),
        WRONGPASSWORD(11),
        DUBLICATELOGIN(21),
        DUBLICATEEMAIL(22),
        EMPTYLOGIN(23),
        EMPTYEMAIL(24),
        EMPTYPASSWORD(25),
        UNEXPECTEDERROR(900);

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
