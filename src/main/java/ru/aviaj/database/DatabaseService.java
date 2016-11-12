package ru.aviaj.database;

import java.sql.Connection;

public abstract class DatabaseService {

    protected ConnectionFactory connectionFactory = new ConnectionFactory();

    protected abstract Connection getConnection();
}
