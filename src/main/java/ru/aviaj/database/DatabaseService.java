package ru.aviaj.database;

import ru.aviaj.database.exception.DbException;

import java.sql.Connection;

public abstract class DatabaseService {

    protected abstract Connection getConnection() throws DbException;

}
