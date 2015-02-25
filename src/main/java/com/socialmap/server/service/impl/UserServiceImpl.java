package com.socialmap.server.service.impl;

import com.socialmap.server.ApplicationProperties;
import com.socialmap.server.ServerDefaults;
import com.socialmap.server.dao.TeamDao;
import com.socialmap.server.dao.UserDao;
import com.socialmap.server.model.Image;
import com.socialmap.server.model.Team;
import com.socialmap.server.model.User;
import com.socialmap.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @Autowired
    TeamDao teamDao;

    @Autowired
    ApplicationProperties props;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findUserByUsername(username);
    }

    @Override
    public void register(User user) {
        // encrypt password
        // digest authentication need clear-text password
        // but we can store HEX(MD5(username:realm:password)) in password
        // and DigestAuthenticationFilter#setPasswordAlreadyEncoded(true)
        String s = String.format("%s:%s:%s", user.getUsername(), props.realm(), user.getPassword());
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        String hex = new String(Hex.encode(digest.digest(s.getBytes())));
        user.setPhone(hex);
        userDao.create(user);
    }

    public User currentUser() {
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
            pro.put("avatar_id", user.getAvatar().getId() + "");
        }
        // bgimage
        if (user.getBgimage() != null){
            pro.put("bgimage_id", user.getBgimage().getId() + "");
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
        } else {
            avatar = new Image();
            avatar.setName(user.getUsername() + "-avatar");
            avatar.setData(data);
        }
        if (!avatar.equals(user.getAvatar())) {
            user.setAvatar(avatar);
            userDao.update(user);
        }
    }

    @Override
    public void updateBgimage(byte[] data) {
        Image bgimage;
        User current = currentUser();
        if (data == null) {
            // use system default bgimage
            bgimage = ServerDefaults.userBgimage();
        } else {
            bgimage = new Image();
            bgimage.setName(current.getUsername() + "-bgimage");
            bgimage.setData(data);
        }
        if (!bgimage.equals(current.getBgimage())) {
            current.setBgimage(bgimage);
            userDao.update(current);
        }
    }

    @Override
    public List myFriends(String filter) {
        return userDao.friends(filter);
    }

    @Override
    public void addFriend(long id) {
        User current = currentUser();
        User friend = userDao.findUserById(id);
        current.getFriends().add(friend);
        userDao.update(current);
    }

    @Override
    public void delFriend(long id) {
        User current = currentUser();
        for(User u: current.getFriends()){
            if(u.getId() == id) {
                current.getFriends().remove(u);
                userDao.update(current);
                break;
            }
        }
    }

    @Override
    public List myTeams(String filter) {
        return userDao.teams(filter);
    }

    @Override
    public void joinTeam(long id) {
        User current = currentUser();
        Team team = teamDao.findTeamById(id);
        current.getTeams().add(team);
        userDao.update(current);
    }

    @Override
    public void quitTeam(long id) {
        User current = currentUser();
        for(Team t: current.getTeams()){
            if(t.getId() == id) {
                current.getTeams().remove(t);
                userDao.update(current);
                break;
            }
        }
    }

    @Override
    public List search(String filter) {
        return userDao.users(filter);
    }

    @Override
    public Map<String, String> friendInfo(long id) {
        Map<String, String> info = new HashMap<>();
        User friend = userDao.findUserById(id);
        if(friend.getUsername()!=null)
            info.put("username", friend.getUsername());
        if(friend.getPhone()!=null)
            info.put("phone", friend.getPhone());
        if(friend.getEmail()!=null)
            info.put("email", friend.getEmail());
        if(friend.getAvatar()!=null)
            info.put("avatar_id", friend.getAvatar().getId() + "");
        return null;
    }
}
