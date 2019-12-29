package com.timw.iprwc.services;

import com.timw.iprwc.models.User;
import io.dropwizard.auth.Authorizer;

public class AuthorizationService implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        return role.equals("admin") && user.isAdmin;
    }

}
