package com.socialmap.server;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created by yy on 2/23/15.
 */
@Component
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

    @Resource
    Environment env;

    public String jdbcUrl() {
        StringBuilder url = new StringBuilder("jdbc:");
        String type = env.getProperty("db.type");
        switch (type) {
            case "mariadb":
                url.append("mariadb://");
                break;
            case "mysql":
                url.append("mysql://");
                break;
            default:
                throw new RuntimeException("Unsupported database type: " + type);
        }
        url.append(env.getProperty("db.host")).append(":")
                .append(env.getProperty("db.port")).append("/").append(env.getProperty("db.name"));
        return url.toString();
    }

    public String jdbcDriver() {
        String type = env.getProperty("db.type");
        switch (type) {
            case "mariadb":
                return "org.mariadb.jdbc.Driver";
            case "mysql":
                return "com.mysql.jdbc.Driver";
            default:
                throw new RuntimeException("Unsupported database type: " + type);
        }
    }

    public String jdbcUsername() {
        return env.getProperty("db.username");
    }

    public String jdbcPassword() {
        return env.getProperty("db.password");
    }

    public Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.dialect"
                , env.getProperty("hibernate.dialect"));
        props.put("hibernate.show_sql"
                , env.getProperty("hibernate.show_sql"));
        props.put("hibernate.format_sql"
                , env.getProperty("hibernate.format_sql"));
        props.put("hibernate.hbm2ddl.auto"
                , env.getProperty("hibernate.hbm2ddl.auto"));
        props.put("hibernate.globally_quoted_identifiers"
                , env.getProperty("hibernate.globally_quoted_identifiers"));
        return props;
    }

    public String realm() {
        return env.getProperty("digest.realm");
    }

    public String key() {
        return env.getProperty("digest.key");
    }
}
