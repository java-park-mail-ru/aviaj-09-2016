package ru.aviaj.database;

import java.sql.Connection;

public abstract class DatabaseService {

    private ConnectionFactory connectionFactory;

    protected abstract Connection getConnection();
}
