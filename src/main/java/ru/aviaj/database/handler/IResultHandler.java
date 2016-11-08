package ru.aviaj.database.handler;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface IResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
