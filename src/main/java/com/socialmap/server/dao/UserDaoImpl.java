package com.socialmap.server.dao;

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
    public Long findIdByUsername(String username) {
        List result = getHibernateTemplate().find("SELECT id FROM users WHERE username=?", username);
        if (result.size() <= 0) {
            throw new UserNotFoundException(String.format("User(name = %s) dose not exist", username));
        }
        return (Long) result.get(0);
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
    public void deleteById(Long id) {
        getHibernateTemplate().delete(findUserById(id));
    }

    @Override
    public List findUsers(String filter) {
        // Given the filter has been checked and is secure
        return getHibernateTemplate().find("SELECT id FROM users WHERE ?", filter);
    }
}
