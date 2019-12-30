package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterForm {

    @JsonProperty("email")
    public String email;
    @JsonProperty("name")
    public String name;
    @JsonProperty("password")
    public String password;

    public RegisterForm(@JsonProperty("email") String email, @JsonProperty("name") String name, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
