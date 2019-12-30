package com.timw.iprwc.resources;

import com.timw.iprwc.db.CartDAO;
import com.timw.iprwc.models.CartItem;
import com.timw.iprwc.models.User;
import com.timw.iprwc.services.CartService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/cart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CartResource {

    private CartDAO cartDAO;

    public CartResource(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @GET
    @UnitOfWork
    public List<CartItem> listCartItems(@Auth User user) {

        return cartDAO.findAll(user);

    }

    @POST
    @UnitOfWork
    public Optional<CartItem> addToCart(@Auth User user, CartItem cartItem) {

        return CartService.addToCart(user, cartItem);

    }

    @GET
    @Path("/{productId}")
    @UnitOfWork
    public Optional<CartItem> readCartItem(@Auth User user, @PathParam("productId") String productId) {

        return cartDAO.findById(user, productId);

    }

    @DELETE
    @Path("/{productId}")
    @UnitOfWork
    public void deleteCartItem(@Auth User user, @PathParam("productId") String productId) {

        cartDAO.delete(user, productId);

    }

    @DELETE
    @UnitOfWork
    public void clearCart(@Auth User user) {

        cartDAO.clear(user);

    }

    @GET
    @Path("/checkout")
    @UnitOfWork
    public void checkout(@Auth User user) {

        CartService.checkout(user);

    }

}
