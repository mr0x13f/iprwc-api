package com.timw.iprwc.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/orders")
public class OrderResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listOrders() {
        return "";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createOrder() {
        return "";
    }

    @GET
    @Path("{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String readOrder() {
        return "";
    }

}
