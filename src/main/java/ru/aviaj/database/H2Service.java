package ru.aviaj.database;


import java.sql.Connection;

public abstract class H2Service implements IDataBaseService {

    private static final DBType DB_TYPE = DBType.H2;
    private String dbUser, dbPassword;

    public DBType getDbType() { return DB_TYPE; }

    private Connection getConnection() {
        //TODO: создание соединения
        return null;
    }

    public H2Service() {
        //TODO: получение логина и пароля
    }
}
