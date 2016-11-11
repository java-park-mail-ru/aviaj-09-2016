package ru.aviaj.database;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class H2Service implements IDataBaseService {

    private static final DBType DB_TYPE = DBType.H2;

    public DBType getDbType() { return DB_TYPE; }

    private Connection getConnection() {
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
