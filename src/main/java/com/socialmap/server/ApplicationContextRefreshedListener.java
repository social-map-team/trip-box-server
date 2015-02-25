package com.socialmap.server;

import com.socialmap.server.test.AddDefaultUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by yy on 2/23/15.
 */
@Component
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LogManager.getLogger();
    
    @Autowired
    AddDefaultUser test;

    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // enter here after app-context initialization completed
        log.trace("Application context refreshed");
        //test.add();
        ServerDefaults.initialize(sessionFactory);
    }
}
