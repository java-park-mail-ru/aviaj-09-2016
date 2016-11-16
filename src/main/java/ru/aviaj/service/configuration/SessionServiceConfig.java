package ru.aviaj.service.configuration;


import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SuppressWarnings({"SpringFacetCodeInspection", "unused"})
@Configuration
public class SessionServiceConfig {
    @Primary
    @Bean(name = "sessionServiceDs")
    @ConfigurationProperties(prefix = "datasource.sessionService")
    public DataSource sessionServiceDs() {
        return DataSourceBuilder.create().build();
    }
}
