package com.socialmap.server.controller;

import com.socialmap.server.exception.FileUploadException;
import com.socialmap.server.model.User;
import com.socialmap.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.io.IOException;
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
        // TODO Need to encrypt password
        System.out.println(user.getUsername());
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
}
