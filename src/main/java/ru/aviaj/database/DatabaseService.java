package ru.aviaj.database;

import java.sql.Connection;

public abstract class DatabaseService {

    //protected ConnectionFactory connectionFactory;

    protected abstract Connection getConnection();
}
