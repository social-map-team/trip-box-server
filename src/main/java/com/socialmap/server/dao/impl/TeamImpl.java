package com.socialmap.server.dao.impl;

import com.socialmap.server.dao.TeamDao;
import com.socialmap.server.model.Team;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by yy on 2/28/15.
 */
@Repository
public class TeamImpl extends HibernateDaoSupport implements TeamDao {

    @Autowired
    public TeamImpl(SessionFactory sessionFactory){
        setSessionFactory(sessionFactory);
    }

    @Override
    public Team findTeamById(long id) {
        return getHibernateTemplate().get(Team.class, id);
    }
}
