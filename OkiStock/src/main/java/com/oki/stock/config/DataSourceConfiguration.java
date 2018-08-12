package com.oki.stock.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
@MapperScan("com.oki.stock.dao")
public class DataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String driverClass;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Bean(name = "dataSource")
    public ComboPooledDataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setAutoCommitOnClose(false);

        dataSource.setPreferredTestQuery("SELECT 1");
        dataSource.setTestConnectionOnCheckout(false);
        dataSource.setTestConnectionOnCheckin(true);
        dataSource.setIdleConnectionTestPeriod(180);
        dataSource.setMaxIdleTime(600);

        dataSource.setInitialPoolSize(5);
        dataSource.setMaxPoolSize(50);
        dataSource.setMinPoolSize(5);
        dataSource.setMaxStatements(500);

        dataSource.setAcquireIncrement(3);
        dataSource.setAcquireRetryAttempts(5);
        dataSource.setAcquireRetryDelay(1000);
        dataSource.setBreakAfterAcquireFailure(false);
        dataSource.setCheckoutTimeout(2000);

        dataSource.setNumHelperThreads(3);

        return dataSource;
    }
}
