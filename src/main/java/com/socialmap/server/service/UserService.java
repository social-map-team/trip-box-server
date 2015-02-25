package com.socialmap.server.service;

import com.socialmap.server.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 * Created by yy on 2/23/15.
 */
public interface UserService extends UserDetailsService {
    public void login();

    public void logout();

    public void register(User user);

    public void unregister();

    public Map<String, String> profile();

    public void update(Map<String, String> changes);

    public void updateAvatar(byte[] data);

    public void updateBgimage(byte[] data);

    public List myFriends(String filter);

    public void addFriend(long id);

    public void delFriend(long id);

    public Map<String, String> friendInfo(long id);

    public List myTeams(String filter);

    public void joinTeam(long id);

    public void quitTeam(long id);

    public List search(String filter);
}
