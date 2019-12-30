package com.timw.iprwc.services;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.RegisterForm;
import com.timw.iprwc.models.User;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import java.util.Optional;

public class UserService {

    // A common minimal password length is 8. We will not pester the user with further requirements.
    private static final int PASSWORD_MINIMAL_LENGTH = 8;
    // It is possible for a person's name to be 1 character long. A name with no characters is not a name.
    // Source: https://digitalcommons.butler.edu/cgi/viewcontent.cgi?article=5327&context=wordways
    private static final int NAME_MINIMAL_LENGTH = 1;

    private static EmailValidator emailValidator = new EmailValidator();
    private static UserDAO userDAO;

    public static void setUserDAO(UserDAO userDAO) {
        UserService.userDAO = userDAO;
    }

    public static Optional<User> register(RegisterForm registerForm) {

        if (!validateForm(registerForm))
            return Optional.empty();

        User user = new User(registerForm);
        user = userDAO.create(user);

        return Optional.of(user);

    }

    private static boolean validateForm(RegisterForm registerForm) {

        return registerForm.password.length() >= PASSWORD_MINIMAL_LENGTH
                && registerForm.name.length() >= NAME_MINIMAL_LENGTH
                // We trust Hibernate to validate email addresses. Sending automated emails for proper validation is beyond the scope of this project.
                && emailValidator.isValid(registerForm.email, null)
                // No 2 users can have the same email address.
                && !userDAO.findByEmail(registerForm.email).isPresent();

    }

}
