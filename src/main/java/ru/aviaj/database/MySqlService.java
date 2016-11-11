package ru.aviaj.database;

import java.sql.Connection;

public abstract class MySqlService implements IDataBaseService {

    private static final DbType dbType = DbType.MYSQL;
    private String dbUser, dbPassword;

    private DbType getDbType() { return dbType; }

    private Connection getConnection() {
        //TODO: создание соединения
        return null;
    }

    public MySqlService() {
        //TODO: получение логина и пароля из системных переменных
    }
}
