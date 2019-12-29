package com.timw.iprwc.services;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.Optional;

public class AuthenticationService implements Authenticator<BasicCredentials, User> {

    private UserDAO userDAO;

    public AuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials credentials) {

        Optional<User> optionalUser = userDAO.findByEmail(credentials.getUsername());

        if (!optionalUser.isPresent()) return Optional.empty(); // No user with matching email found

        User user = optionalUser.get();
        String saltedHash = hashWithSalt(credentials.getPassword(), user.passwordSalt);

        if (!user.passwordHash.equals(saltedHash)) return Optional.empty(); // Passwords don't match

        return Optional.of(user);

    }

    public static String generateSalt() {
        //TODO ////////////////////////////////////////////////////
        return "funny";
    }

    public static String hashWithSalt(String password, String salt) {
        //TODO ////////////////////////////////////////////////////
        return password+"."+salt;
    }
}
