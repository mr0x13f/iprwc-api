package com.timw.iprwc.resources;

import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.models.Product;
import com.timw.iprwc.models.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private ProductDAO productDAO;

    public ProductResource(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GET
    @UnitOfWork
    public List<Product> listProducts() {

        return productDAO.findAll();

    }

    @POST
    @RolesAllowed("admin")
    @UnitOfWork
    public Product createProduct(@Auth User user, Product product) {

        return productDAO.create(product);

    }

    @GET
    @Path("{productId}")
    @UnitOfWork
    public Optional<Product> readProduct(@PathParam("productId") String productId) {

        return productDAO.findById(productId);

    }

    @PUT
    @Path("{productId}")
    @RolesAllowed("admin")
    @UnitOfWork
    public Product updateProduct(@Auth User user, @PathParam("productId") String productId, Product product) {

        return productDAO.update(productId, product);


    }

    @DELETE
    @Path("{productId}")
    @RolesAllowed("admin")
    @UnitOfWork
    public void deleteProduct(@Auth User user, @PathParam("productId") String productId) {

        productDAO.delete(productId);

    }

}
