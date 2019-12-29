package com.timw.iprwc.resources;

import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.Product;
import com.timw.iprwc.models.User;
import com.timw.iprwc.services.DatabaseService;
import com.timw.iprwc.services.JacksonService;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/debug")
public class DebugResource {

    private UserDAO userDAO;
    private ProductDAO productDAO;

    public DebugResource(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @POST
    @Path("/product")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public String postProduct(String productData) {

        Product product = (Product) JacksonService.fromJson(productData, Product.class);

        String out = "";
        out += "product_id: " + product.productId + "\n";
        out += "name: " + product.name + "\n";
        out += "description: " + product.description + "\n";
        out += "price: " + product.price + "\n";

        return out;
    }

    @GET
    @Path("/auth")
    @UnitOfWork
    public String authTest(@Auth User user) {
        return "Welcome, " + user.name;
    }

}
