package ru.aviaj.database;


import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class ConnectionFactory {

    private String mysqlUser;
    private String mysqlPassword;

    public ConnectionFactory() {
        final Map<String, String> envVar = System.getenv();
        mysqlUser = envVar.get("AVIAJ_MYSQL_USER");
        mysqlPassword = envVar.get("AVIAJ_MYSQL_PASSWORD");

        if (StringUtils.isEmpty(mysqlUser) || StringUtils.isEmpty(mysqlPassword)) {
            System.out.println("AVIAJ_MYSQL_USER or AVIAJ_MYSQL_PASSWORD are not set in environment!");
        }
    }

    public Connection getMySQLConnection() {
        try {
            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://localhost:3306/Aviaj?user=" + mysqlUser + "&password=" + mysqlPassword;

            return DriverManager.getConnection(url);
        }
        catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getH2Connection() {
        try {
            Driver driver = (Driver) Class.forName("org.h2.Driver").newInstance();
            DriverManager.registerDriver(driver);
            String url = "jdbc:h2:mem:Aviaj";

            return DriverManager.getConnection(url);
        }
        catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
