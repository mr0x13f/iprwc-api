package com.timw.iprwc.services;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.RegisterForm;
import com.timw.iprwc.models.User;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;

public class AuthenticationService implements Authenticator<BasicCredentials, User> {

    private static final int SALT_SIZE = 8;

    private static Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static Base64.Decoder decoder = Base64.getUrlDecoder();

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
        String saltedHash = null;

        saltedHash = hashWithSalt(credentials.getPassword(), user.passwordSalt);

        if (!user.passwordHash.equals(saltedHash)) return Optional.empty(); // Passwords don't match

        return Optional.of(user);

    }

    public static String generateSalt() {

        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[SALT_SIZE];
        random.nextBytes(salt);

        return encoder.encodeToString(salt);

    }

    public static String hashWithSalt(String password, String salt) {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), decoder.decode(salt), 65536, 128);
        byte[] hash = null;

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return encoder.encodeToString(hash);

    }

}
