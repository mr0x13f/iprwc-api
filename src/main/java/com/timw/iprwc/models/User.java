package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.timw.iprwc.services.BasicAuthenticationService;
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
        @NamedQuery(name = "iprwc.User.findByEmail",
            query = "select u from User u"
                    + " where u.email = :email"),

        @NamedQuery(name = "iprwc.User.findById",
                query = "select u from User u"
                        + " where u.userId = :userId"),

        @NamedQuery(name = "iprwc.User.delete",
                query = "delete from User u"
                        + " where u.userId = :userId")
})
public class User implements java.security.Principal  {

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

    public User() {}

    public User(RegisterForm registerForm) {

        name = registerForm.name;
        email = registerForm.email;
        isAdmin = false;

        userId = UUID.randomUUID().toString();
        passwordSalt = BasicAuthenticationService.generateSalt();
        passwordHash = BasicAuthenticationService.hashWithSalt(registerForm.password, passwordSalt);

    }

    @Override
    public String getName() {
        return name;
    }

}
