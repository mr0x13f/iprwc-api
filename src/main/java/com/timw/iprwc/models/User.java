package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timw.iprwc.services.AuthenticationService;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "iprwc.User.findAll",
            query = "select u from User u"),

        @NamedQuery(name = "iprwc.User.findByEmail",
            query = "select u from User u"
                    + " where u.email = :email")
})
public class User implements java.security.Principal {

    @Id
    @Column(name = "user_id")
    public String userId;
    @Column(name = "is_admin")
    public boolean isAdmin;
    public String name;
    public String email;
    @Column(name = "password_hash")
    @JsonIgnore
    public String passwordHash;
    @JsonIgnore
    @Column(name = "password_salt")
    public String passwordSalt;

    @Override
    public String getName() {
        return name;
    }

    public User(RegisterForm registerForm) {

        name = registerForm.name;
        email = registerForm.email;
        isAdmin = false;

        userId = UUID.randomUUID().toString();
        passwordSalt = AuthenticationService.generateSalt();
        passwordHash = AuthenticationService.hashWithSalt(registerForm.password, passwordSalt);

    }

}
