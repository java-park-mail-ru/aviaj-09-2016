package ru.aviaj.database;

import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public abstract class MySqlService implements IDataBaseService {

    private static final DBType DB_TYPE = DBType.MYSQL;
    private String dbUser, dbPassword;

    public DBType getDbType() { return DB_TYPE; }

    private Connection getConnection() {
        //TODO: добавить Connecion Pool
        try {
            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://localhost:3306/Aviaj?user=" + dbUser + "&password=" + dbPassword;

            return DriverManager.getConnection(url);
        }
        catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MySqlService() {

        Map<String, String> envVar = System.getenv();
        dbUser = envVar.get("AVIAJ_MYSQL_USER");
        dbPassword = envVar.get("AVIAJ_MYSQL_PASSWORD");

        if (StringUtils.isEmpty(dbUser) || StringUtils.isEmpty(dbPassword)) {
            System.out.println("AVIAJ_MYSQL_USER or AVIAJ_MYSQL_PASSWORD are not set in environment!");
        }
    }
}
