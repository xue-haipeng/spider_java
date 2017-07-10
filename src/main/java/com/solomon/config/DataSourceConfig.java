package com.solomon.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by xuehaipeng on 2017/7/10.
 */
@Configuration
public class DataSourceConfig {

/*    @Bean(name = "localDataSource")
    @Qualifier("localDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.local")
    public DataSource localDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "qatDataSource")
    @Qualifier("qatDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.qat")
    public DataSource qatDataSource() {
        return DataSourceBuilder.create().build();
    }
    */
}
