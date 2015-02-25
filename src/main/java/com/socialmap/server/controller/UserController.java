package com.socialmap.server.controller;

import com.socialmap.server.exception.FileUploadException;
import com.socialmap.server.model.User;
import com.socialmap.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.socialmap.server.utils.ApiUrls.User.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * Created by yy on 2/23/15.
 */
@RestController
@RequestMapping(value = ROOT, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = LOGIN, method = POST)
    public void login() {
        userService.login();
    }

    @RequestMapping(value = LOGOUT, method = POST)
    public void logout() {
        userService.logout();
    }

    @RequestMapping(value = REGISTER, method = POST)
    @PermitAll
    public void register(@Valid User user) {
        userService.register(user);
    }

    @RequestMapping(value = UNREGISTER, method = DELETE)
    public void unregister() {
        userService.unregister();
    }

    @RequestMapping(value = PROFILE, method = GET)
    @ResponseBody
    public Map<String, String> profile() {
        return userService.profile();
    }

    @RequestMapping(value = UPLOAD_AVATAR, method = PUT)
    public void uploadAvatar(@RequestParam("image") MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                userService.updateAvatar(bytes);
            } catch (IOException e) {
                throw new FileUploadException(e);
            }
        } else {
            userService.updateAvatar(null);
        }
    }

    @RequestMapping(value = UPLOAD_BGIMAGE, method = PUT)
    public void uploadBgimage(@RequestParam("image") MultipartFile image) {
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                userService.updateBgimage(bytes);
            } catch (IOException e) {
                throw new FileUploadException(e);
            }
        } else {
            userService.updateBgimage(null);
        }
    }

    @RequestMapping(value = SEARCH, method = GET)
    public List search(@RequestParam(required = false, defaultValue = "") String filter) {
        return userService.search(filter);
    }

    @RequestMapping(value = UPDATE_PROFILE, method = PUT)
    public void updateProfile(Map<String, String> changes) {
        userService.update(changes);
    }

    @RequestMapping(value = MY_FRIENDS, method = GET)
    public List myFriends(@RequestParam(required = false, defaultValue = "") String filter) {
        return userService.myFriends(filter);
    }

    @RequestMapping(value = ADD_FRIEND, method = POST)
    public void addFriend(@PathVariable long id) {
        userService.addFriend(id);
    }

    @RequestMapping(value = DEL_FRIEND, method = DELETE)
    public void deleteFriend(@PathVariable long id) {
        userService.delFriend(id);
    }

    @RequestMapping(value = FRIEND_INFO, method = GET)
    public Map<String, String> friendInfo(@PathVariable long id) {
        return userService.friendInfo(id);
    }

    @RequestMapping(value = MY_TEAMS, method = GET)
    public List myTeams(@RequestParam(required = false, defaultValue = "") String filter) {
        return userService.myTeams(filter);
    }

    @RequestMapping(value = JOIN_TEAM, method = POST)
    public void joinTeam(@PathVariable long id) {
        userService.joinTeam(id);
    }

    @RequestMapping(value = QUIT_TEAM, method = DELETE)
    public void quitTeam(@PathVariable long id) {
        userService.quitTeam(id);
    }

}
