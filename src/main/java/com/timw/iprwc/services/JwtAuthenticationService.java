package com.timw.iprwc.services;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

import java.util.Optional;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

public class JwtAuthenticationService implements Authenticator<JwtContext, User> {

    public static final byte[] JWT_SECRET_KEY = "dfwzsdzwh823zebdwdz772632gdsbd3333".getBytes();

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


    public static JsonWebSignature buildToken(User user) {
        // These claims would be tightened up for production
        final JwtClaims claims = new JwtClaims();
        claims.setSubject("1");
        claims.setStringClaim("userId", user.userId);
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(HMAC_SHA256);
        jws.setKey(new HmacKey(JwtAuthenticationService.JWT_SECRET_KEY));
        return jws;
    }
}
