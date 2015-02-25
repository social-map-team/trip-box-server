package com.socialmap.server;

import com.socialmap.server.exception.ServerInitializationException;
import com.socialmap.server.model.Authority;
import com.socialmap.server.model.Image;
import com.socialmap.server.model.User;
import com.socialmap.server.utils.Security;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yy on 2/23/15.
 */
@Component
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    SessionFactory sessionFactory;

    private void inflateData() {
        Authority a1 = new Authority("ROLE_ADMIN");
        Authority a2 = new Authority("ROLE_USER");
        Authority a3 = new Authority("ROLE_TEST");
        Authority a4 = new Authority("ROLE_TOURIST");
        Authority a5 = new Authority("ROLE_GUIDE");

        Image avatarImg = null;
        Image bgimageImg = null;
        try {
            File _avatar = new File("db/default/user-avatar.png");
            InputStream avatar;
            if (_avatar.exists()) {
                avatar = new FileInputStream(_avatar);
            } else {
                avatar = App.class.getResourceAsStream("/default-user-avatar.png");
            }
            avatarImg = new Image("default-user-avatar", IOUtils.toByteArray(avatar));

            File _bgimage = new File("db/default/user-bgimage.png");
            InputStream bgimage;
            if (_bgimage.exists()) {
                bgimage = new FileInputStream(_bgimage);
            } else {
                bgimage = App.class.getResourceAsStream("/default-user-bgimage.png");
            }
            bgimageImg = new Image("default-user-bgimage", IOUtils.toByteArray(bgimage));
        } catch (IOException e) {
            throw new ServerInitializationException(e);
        }

        User u = new User();
        u.setUsername("test");
        u.setPassword("123");
        u.setAvatar(avatarImg);
        u.setBgimage(bgimageImg);
        u.getAuthorities().add(a3);
        Security.encryptPassword(u);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(a1);
        session.save(a2);
        session.save(a3);
        session.save(a4);
        session.save(a5);
        session.save(avatarImg);
        session.save(bgimageImg);
        session.save(u);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // enter here after app-context initialization completed
        inflateData();
        ServerDefaults.initialize(sessionFactory);
    }
}
