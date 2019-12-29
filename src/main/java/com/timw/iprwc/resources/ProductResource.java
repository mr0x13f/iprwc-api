package com.timw.iprwc.resources;

import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.models.Product;
import com.timw.iprwc.services.JacksonService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/products")
public class ProductResource {

    private ProductDAO productDAO;

    public ProductResource(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String listProducts() {
        return JacksonService.toJson( productDAO.findAll() );
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String createProduct() {
        return "";
    }

    @GET
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String readProduct(@PathParam("productId") String productId) {
        return JacksonService.toJson(productDAO.findById(productId));
    }

    @PUT
    @Path("{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String updateProduct() {
        return "";
    }

    @DELETE
    @Path("{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String deleteProduct() {
        return "";
    }

}
