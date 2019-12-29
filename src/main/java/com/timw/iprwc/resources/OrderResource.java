package com.timw.iprwc.resources;

import com.timw.iprwc.models.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/orders")
public class OrderResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String listOrders(@Auth User user) {
        return "";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String createOrder(@Auth User user) {
        return "";
    }

    @GET
    @Path("{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String readOrder(@Auth User user) {
        return "";
    }

}
