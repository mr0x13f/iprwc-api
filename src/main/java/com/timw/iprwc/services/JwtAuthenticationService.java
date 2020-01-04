package com.timw.iprwc.services;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

public class JwtAuthenticationService implements Authenticator<JwtContext, User> {

    private static byte[] secretKey;

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

    public JsonWebSignature buildToken(User user) throws NoSuchAlgorithmException {

        final JwtClaims claims = new JwtClaims();
        claims.setSubject("1");
        claims.setStringClaim("userId", user.userId);
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(HMAC_SHA256);
        jws.setKey(new HmacKey(this.getSecretKey()));
        return jws;
    }

    public byte[] getSecretKey() throws NoSuchAlgorithmException {
        if (secretKey != null) return secretKey;

        secretKey = new byte[32];
        SecureRandom.getInstanceStrong().nextBytes(secretKey);
        return secretKey;
    }
}
