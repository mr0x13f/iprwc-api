package com.timw.iprwc.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "iprwc.User.findAll",
            query = "select u from User u"),
        @NamedQuery(name = "iprwc.User.findByEmail",
            query = "select u from User u "
                    + "where u.email = :email")
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
    public String passwordHash;
    @Column(name = "password_salt")
    public String passwordSalt;

    @Override
    public String getName() {
        return "";
    }

}
