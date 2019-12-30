package com.timw.iprwc.resources;

import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.RegisterForm;
import com.timw.iprwc.models.User;
import com.timw.iprwc.services.UserService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @POST
    @Path("/register")
    @UnitOfWork
    public Optional<User> register(RegisterForm registerForm) {

        return UserService.register(registerForm);

    }

}
