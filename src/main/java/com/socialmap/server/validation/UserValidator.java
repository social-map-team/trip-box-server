package com.socialmap.server.validation;

import com.socialmap.server.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by yy on 2/25/15.
 */
public class UserValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
