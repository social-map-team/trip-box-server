package com.socialmap.server.config;

import com.socialmap.server.ApplicationProperties;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by yy on 2/23/15.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    
    @Autowired
    ApplicationProperties props;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource(
                props.jdbcUrl(),props.jdbcUsername(),props.jdbcPassword());
        ds.setDriverClassName(props.jdbcDriver());
        return ds;
    }
    
    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.socialmap.server.model");
        builder.addProperties(props.hibernateProperties());
        return builder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
    
}
