package ru.aviaj.service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(
                    (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance()
            );
            String dbUrl = "jdbc:mysql://localhost:3306/Aviaj?user=root&password=toor";
            Connection connection = DriverManager.getConnection(dbUrl);

            return  connection;
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace(); //Добавить логгирование
        }

        return null;
    }
}
