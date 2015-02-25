package com.socialmap.server;

import com.socialmap.server.exception.ServerInitializationException;
import com.socialmap.server.model.Image;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by yy on 2/25/15.
 */
public class ServerDefaults {
    private static Image userAvatar;
    private static Image userBgimage;

    public static void initialize(SessionFactory sessionFactory){
        HibernateTemplate ht = new HibernateTemplate(sessionFactory);
        try {
            userAvatar = (Image) ht.find("FROM images WHERE name = ?", "default-user-avatar").get(0);
        } catch (IndexOutOfBoundsException e){
            // cannot find default user avatar
            throw new ServerInitializationException("Cannot find default user avatar image in the database.");
        }
        try {
            userBgimage = (Image) ht.find("FROM images WHERE name = ?", "default-user-bgimage").get(0);
        } catch (IndexOutOfBoundsException e){
            // cannot find default user background image
            throw new ServerInitializationException("Cannot find default user background image in the database.");
        }
    }

    public static Image userAvatar() {
        return userAvatar;
    }

    public static Image userBgimage() {
        return userBgimage;
    }
}
