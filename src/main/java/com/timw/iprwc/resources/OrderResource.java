package com.timw.iprwc.resources;

import com.timw.iprwc.models.Order;
import com.timw.iprwc.models.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @GET
    @UnitOfWork
    public List<Order> listOrders(@Auth User user) {
        return null;
    }

    @GET
    @Path("{orderId}")
    @UnitOfWork
    public Optional<Order> readOrder(@Auth User user) {
        return null;
    }

}
