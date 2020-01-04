package com.timw.iprwc.models;

public class BasicAuth implements java.security.Principal {

    public User user;

    public BasicAuth(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.name;
    }

}
