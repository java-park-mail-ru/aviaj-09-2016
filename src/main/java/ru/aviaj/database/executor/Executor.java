package ru.aviaj.database.executor;

import ru.aviaj.database.handler.IResultHandler;

import java.sql.Connection;

public class Executor {
    public <T> T execQuery(Connection dbConnection, String sqlQuery, IResultHandler<T> resultHandler) {
        //TODO: выполнение запроса
        return null;
    }

    public boolean execUpdate(Connection dbConnection, String sqlUpdate) {
        //TODO: выполнение запроса
        return false;
    }
}
