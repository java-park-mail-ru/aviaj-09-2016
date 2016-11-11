package ru.aviaj.database;


import java.sql.Connection;

public abstract class H2Service implements IDataBaseService {

    private static final DbType dbType = DbType.H2;
    private String dbUser, dbPassword;

    public DbType getDbType() { return dbType; }

    private Connection getConnection() {
        //TODO: создание соединения
        return null;
    }

    public H2Service() {
        //TODO: получение логина и пароля
    }
}
