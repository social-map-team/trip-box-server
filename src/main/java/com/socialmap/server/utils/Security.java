package com.socialmap.server.utils;

import com.socialmap.server.App;
import com.socialmap.server.model.User;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yy on 3/1/15.
 */
public class Security {
    public static void encryptPassword(User user){
        String s = String.format("%s:%s:%s", user.getUsername(), App.realm(), user.getPassword());
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        String hex = new String(Hex.encode(digest.digest(s.getBytes())));
        user.setPassword(hex);
    }
}
