package ru.aviaj.database;


import java.sql.Connection;

public abstract class H2Service implements IDataBaseService {

    private static final DBType DB_TYPE = DBType.H2;

    public DBType getDbType() { return DB_TYPE; }

    private Connection getConnection() {
        //jdbc:h2:mem:Aviaj
        
        return null;
    }

}
