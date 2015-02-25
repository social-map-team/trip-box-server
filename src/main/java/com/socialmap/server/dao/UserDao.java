package com.socialmap.server.dao;


import com.socialmap.server.model.User;

import java.util.List;

/**
 * Created by yy on 2/23/15.
 */
public interface UserDao {
    public User findUserByUsername(String username);

    public User findUserById(Long id);

    public void create(User user);

    public void update(User user);

    public void delete(User user);

    public List friends(String filter);

    public List teams(String filter);

    public List users(String filter);
}
