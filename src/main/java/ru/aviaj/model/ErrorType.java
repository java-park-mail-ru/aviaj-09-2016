package ru.aviaj.model;

@SuppressWarnings("unused")
public enum ErrorType {
    WRONGUSERID(9),
    NOLOGIN(10),
    WRONGPASSWORD(11),
    DUBLICATELOGIN(21),
    DUBLICATEEMAIL(22),
    EMPTYLOGIN(23),
    EMPTYEMAIL(24),
    EMPTYPASSWORD(25),
    NOUSERID(100),
    ALREADYLOGIN(101),
    NOTLOGINED(102),
    UNEXPECTEDERROR(900),
    NOTREALISED(901),
    WRONGTYPE(902),
    WRONGBODY(903),
    DBERROR(951);

    private int code;

    ErrorType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
