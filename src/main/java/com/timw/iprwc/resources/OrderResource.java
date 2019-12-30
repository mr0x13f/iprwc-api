package com.timw.iprwc.resources;

import com.timw.iprwc.models.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @GET
    @UnitOfWork
    public String listOrders(@Auth User user) {
        return "";
    }

    @POST
    @UnitOfWork
    public String createOrder(@Auth User user) {
        return "";
    }

    @GET
    @Path("{orderId}")
    @UnitOfWork
    public String readOrder(@Auth User user) {
        return "";
    }

}
