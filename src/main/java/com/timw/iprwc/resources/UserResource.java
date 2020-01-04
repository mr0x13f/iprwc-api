package com.timw.iprwc.resources;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.BasicAuth;
import com.timw.iprwc.models.LoginResponse;
import com.timw.iprwc.models.RegisterForm;
import com.timw.iprwc.models.User;
import com.timw.iprwc.services.JwtAuthenticationService;
import com.timw.iprwc.services.UserService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import org.jose4j.lang.JoseException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @UnitOfWork
    public User getSelf(@Auth User user) {

        return user;

    }

    @GET
    @Path("/token")
    @UnitOfWork
    public LoginResponse token(@Auth BasicAuth basicAuth) throws JoseException {
        return new LoginResponse(JwtAuthenticationService.buildToken(basicAuth.user).getCompactSerialization());
    }

    @POST
    @Path("/register")
    @UnitOfWork
    public Optional<User> register(RegisterForm registerForm) {

        return UserService.register(registerForm);

    }

    @PUT
    @UnitOfWork
    public Optional<User> update(@Auth User user, RegisterForm registerForm) {

        return UserService.update(user, registerForm);

    }

    @DELETE
    @UnitOfWork
    public void delete(@Auth User user) {

        userDAO.delete(user.userId);

    }

    @GET
    @Path("/gdpr")
    @UnitOfWork
    public void gdpr(@Auth User user) {

    }

}
