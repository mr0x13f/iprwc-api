package com.timw.iprwc.resources;

import com.timw.iprwc.db.WishlistDAO;
import com.timw.iprwc.models.User;
import com.timw.iprwc.models.WishlistItem;
import com.timw.iprwc.services.WishlistService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/wishlist")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WishlistResource {

    private WishlistDAO wishlistDAO;

    public WishlistResource(WishlistDAO wishlistDAO) {
        this.wishlistDAO = wishlistDAO;
    }

    @GET
    @UnitOfWork
    public List<WishlistItem> listWishlistItems(@Auth User user) {

        return wishlistDAO.findAll(user);

    }

    @POST
    @UnitOfWork
    public Optional<WishlistItem> addToWishlist(@Auth User user, WishlistItem wishlistItem) {

        return WishlistService.addToWishlist(user, wishlistItem);

    }

    @GET
    @Path("/{productId}")
    @UnitOfWork
    public Optional<WishlistItem> readWishlistItem(@Auth User user, @PathParam("productId") String productId) {

        return wishlistDAO.findById(user, productId);

    }

    @DELETE
    @Path("/{productId}")
    @UnitOfWork
    public void deleteWishlistItem(@Auth User user, @PathParam("productId") String productId) {

        wishlistDAO.delete(user, productId);

    }

    @DELETE
    @UnitOfWork
    public void clearWishlist(@Auth User user) {

        wishlistDAO.clear(user);

    }

}
