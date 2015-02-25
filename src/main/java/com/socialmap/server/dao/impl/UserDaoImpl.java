package com.socialmap.server.dao.impl;

import com.socialmap.server.dao.UserDao;
import com.socialmap.server.exception.UserNotFoundException;
import com.socialmap.server.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by yy on 2/23/15.
 */
@Repository
@Transactional
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public User findUserByUsername(String username) {
        List result = getHibernateTemplate().find("FROM users WHERE username=?", username);
        if (result.size() <= 0) {
            throw new UserNotFoundException(String.format("User(name = %s) dose not exist", username));
        }
        return (User) result.get(0);
    }

    @Override
    public void create(User user) {
        getHibernateTemplate().save(user);
    }

    @Override
    public User findUserById(Long id) throws UserNotFoundException {
        User user = getHibernateTemplate().get(User.class, id);
        return user;
    }

    @Override
    public void update(User user) {
        getHibernateTemplate().update(user);
    }

    @Override
    public void delete(User user) {
        getHibernateTemplate().delete(user);
    }

    @Override
    public List friends(String filter) {
        if (filter.isEmpty()) {
            return getHibernateTemplate().find("SELECT id FROM users.friends");
        }
        // TODO SQL Injection Problem
        return getHibernateTemplate().find("SELECT id FROM users.friends WHERE " + filter);
    }

    @Override
    public List teams(String filter) {
        if (filter.isEmpty()) {
            return getHibernateTemplate().find("SELECT id FROM users.teams");
        }
        // TODO SQL Injection Problem
        return getHibernateTemplate().find("SELECT id FROM users.teams WHERE " + filter);
    }

    @Override
    public List users(String filter) {
        if (filter.isEmpty()) {
            return getHibernateTemplate().find("SELECT id FROM users");
        }
        // TODO SQL Injection Problem
        return getHibernateTemplate().find("SELECT id FROM users.friends WHERE " + filter);
    }
}
