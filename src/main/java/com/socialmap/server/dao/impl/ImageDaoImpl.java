package com.socialmap.server.dao.impl;

import com.socialmap.server.dao.ImageDao;
import com.socialmap.server.model.Image;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yy on 2/25/15.
 */
@Repository
public class ImageDaoImpl extends HibernateDaoSupport implements ImageDao {

    @Autowired
    public ImageDaoImpl(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    }

    @Override
    public Image findImageByName(String name) {
        List result = getHibernateTemplate().find("FROM images WHERE name = ?", name);
        return (Image)result.get(0);
    }

    @Override
    public Image findImageById(long id) {
        return getHibernateTemplate().get(Image.class, id);
    }
}
