package com.timw.iprwc.resources;

import com.timw.iprwc.db.OrderDAO;
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

    private OrderDAO orderDAO;

    public OrderResource(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GET
    @UnitOfWork
    public List<Order> listOrders(@Auth User user) {

        return orderDAO.findAll(user);

    }

    @GET
    @Path("{orderId}")
    @UnitOfWork
    public Optional<Order> readOrder(@Auth User user, @PathParam("orderId") String orderId) {

        return orderDAO.findById(user, orderId);

    }

}
