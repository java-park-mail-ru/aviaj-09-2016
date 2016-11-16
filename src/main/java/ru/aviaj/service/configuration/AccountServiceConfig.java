package ru.aviaj.service.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@SuppressWarnings({"SpringFacetCodeInspection", "unused"})
@Configuration
public class AccountServiceConfig {
    @Bean(name = "accountServiceDs")
    @ConfigurationProperties(prefix = "datasource.accountService")
    public DataSource accountServiceDs() {
        return DataSourceBuilder.create().build();
    }
}
