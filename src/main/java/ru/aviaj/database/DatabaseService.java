package ru.aviaj.database;

import java.sql.Connection;

public abstract class DatabaseService {

    protected abstract Connection getConnection() throws DbConnectException;

}
