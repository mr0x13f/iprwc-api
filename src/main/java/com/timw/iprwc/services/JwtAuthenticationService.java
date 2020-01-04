package com.timw.iprwc.services;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtContext;

import java.util.Optional;

public class JwtAuthenticationService implements Authenticator<JwtContext, User> {

    private UserDAO userDAO;

    public JwtAuthenticationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(JwtContext context) {
        try {
            JwtClaims claims = context.getJwtClaims();

            String userId = (String) claims.getClaimValue("userId");

            return userDAO.findById(userId);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
