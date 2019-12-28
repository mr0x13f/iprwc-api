package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {

    public String userId;
    public boolean isAdmin;
    public String name;
    public String email;

    @JsonCreator
    public User(@JsonProperty("userId") String userId, // consume JSON field, but force random UUID
                @JsonProperty("isAdmin") String isAdmin,  // consume JSON field, but force FALSE
                @JsonProperty("name") String name,
                @JsonProperty("email") String email) {

        this.userId = UUID.randomUUID().toString();
        this.isAdmin = false;
        this.name = name;
        this.email = email;
    }

}
