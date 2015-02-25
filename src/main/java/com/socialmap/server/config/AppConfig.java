package com.socialmap.server.config;

import com.socialmap.server.App;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by yy on 3/1/15.
 */
@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan("com.socialmap")
@Import({MvcConfig.class, SecurityConfig.class})
@EnableTransactionManagement
public class AppConfig {
    public static final String DATABASE_DIR = "db/";
    public static final String DATABASE_DEFAULTS = DATABASE_DIR + "defaults/";
    public static final String CONFIG_FILENAME = "settings.properties";
    public static final String DATABASE_NAME = "TBS";
    public static final String JDBC_URL = "jdbc:hsqldb:file:" + DATABASE_DIR + DATABASE_NAME;
    public static final String JDBC_USERNAME = "SA";
    public static final String JDBC_PASSWORD = "";
    public static final String JDBC_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    public static final String HIBERNATE_DIALECT = "org.hibernate.dialect.HSQLDialect";
    public static final String HIBERNATE_HBM2DDL = "create";

    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setUrl(
                env.getProperty("jdbc.url", JDBC_URL));
        dataSource.setUsername(
                env.getProperty("jdbc.username", JDBC_USERNAME));
        dataSource.setPassword(
                env.getProperty("jdbc.password", JDBC_PASSWORD));
        dataSource.setDriverClassName(
                env.getProperty("jdbc.driver", JDBC_DRIVER));
        return dataSource;
    }

    @Autowired
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("com.socialmap.server.model");
        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL));
        props.put("hibernate.dialect", env.getProperty("hibernate.dialect", HIBERNATE_DIALECT));
        props.put("hibernate.globally_quoted_identifiers", "true");
        bean.setHibernateProperties(props);
        App.sessionFactoryBean = bean;
        return bean;
    }

    @Autowired
    @Bean
    public HibernateTransactionManager txManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager =
                new HibernateTransactionManager(sessionFactory);
        return txManager;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * 对任何带有@Repository 注解的对象自动激活其数据访问异常转换
     *
     * @return
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
