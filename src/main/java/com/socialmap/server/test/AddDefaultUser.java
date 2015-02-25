package com.socialmap.server.test;

import com.socialmap.server.ApplicationProperties;
import com.socialmap.server.dao.UserDao;
import com.socialmap.server.model.Authority;
import com.socialmap.server.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yy on 2/23/15.
 */
@Component
@Transactional
public class AddDefaultUser {
    @Autowired
    UserDao userDao;

    @Autowired
    ApplicationProperties props;

    @Autowired
    SessionFactory sessionFactory;

    public void add() {
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        Authority auth = new Authority();
        auth.setName("USER");
        ht.save(auth);

        String username = "test";
        String password = "123";

        // digest password encoding
        String s = String.format("%s:%s:%s", username, props.realm(), password);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        String hex = new String(Hex.encode(digest.digest(s.getBytes())));
        password = hex;

        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setEnabled(true);
        u.getAuthorities().add(auth);
        userDao.create(u);
    }

}
