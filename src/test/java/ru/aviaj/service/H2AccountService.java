package ru.aviaj.service;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class H2AccountService extends AccountService {

    private DataSource ds;

    public H2AccountService(DataSource ds) {
        this.ds = ds;
    }

    @Override
    protected Connection getConnection() {
        return DataSourceUtils.getConnection(ds);
    }
}
