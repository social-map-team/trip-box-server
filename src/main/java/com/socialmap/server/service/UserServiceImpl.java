package com.socialmap.server.service;

import com.socialmap.server.ServerDefaults;
import com.socialmap.server.dao.UserDao;
import com.socialmap.server.model.Image;
import com.socialmap.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yy on 2/23/15.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByUsername(username);
    }

    @Override
    public void register(User user) {
        userDao.create(user);
    }

    private User currentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @Override
    public Map<String, String> profile() {
        Map<String, String> pro = new HashMap<>();
        User user = currentUser();
        // username
        pro.put("username", user.getUsername());
        // idcard
        if (user.getIdcard() != null) {
            pro.put("idcard", user.getIdcard());
        }
        // realname
        if (user.getRealname() != null) {
            pro.put("realname", user.getRealname());
        }
        // birthday
        if (user.getBirthday() != null) {
            pro.put("birthday", new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday()));
        }
        // phone
        if (user.getPhone() != null) {
            pro.put("phone", user.getPhone());
        }
        // email
        if (user.getEmail() != null) {
            pro.put("email", user.getEmail());
        }
        // avatar
        if (user.getAvatar() != null){
            pro.put("avatar", user.getAvatar().getId() + "");
        }
        return pro;
    }

    @Override
    public void login() {
        User user = currentUser();
        user.setStatus(User.Status.ONLINE);
        userDao.update(user);
    }

    @Override
    public void logout() {
        User user = currentUser();
        user.setStatus(User.Status.OFFLINE);
        userDao.update(user);
    }

    @Override
    public void unregister() {
        userDao.delete(currentUser());
    }

    @Override
    public void update(Map<String, String> changes) {
        // user can modify his/her phone or email
        String newPhone = changes.get("phone");
        String newEmail = changes.get("email");
        User user = currentUser();
        boolean changed = false;
        if (!newPhone.equals(user.getPhone())) {
            user.setPhone(newPhone);
            changed = true;
        }
        if (!newEmail.equals(user.getEmail())) {
            user.setEmail(newEmail);
            changed = true;
        }
        if (changed) {
            userDao.update(user);
        }
    }

    @Override
    public void updateAvatar(byte[] data) {
        Image avatar;
        User user = currentUser();
        if (data == null) {
            // use system default avatar
            avatar = ServerDefaults.userAvatar();
            user.setAvatar(avatar);
        } else {
            avatar = new Image();
            avatar.setName(user.getUsername() + "-avatar");
            avatar.setData(data);
            user.setAvatar(avatar);
        }
        if (!avatar.equals(user.getAvatar())) {
            user.setAvatar(avatar);
            userDao.update(user);
        }
    }

    @Override
    public void updateBgimage(byte[] data) {
        if (data == null) {
            // use system default bgimage
        } else {

        }
    }

    @Override
    public List<Long> myFriends(String filter) {
        // when filter is empty, it will return all friends
        return null;
    }

    @Override
    public void addFriend(long id) {

    }

    @Override
    public void delFriend(long id) {
    }

    @Override
    public List<Long> myTeams(String filter) {
        return null;
    }

    @Override
    public void joinTeam(long id) {
    }

    @Override
    public void quitTeam(long id) {
    }

    @Override
    public List<Long> search(String filter) {
        return null;
    }
}
