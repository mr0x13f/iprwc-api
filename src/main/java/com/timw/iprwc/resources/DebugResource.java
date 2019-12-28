package com.timw.iprwc.resources;

import com.timw.iprwc.models.Product;
import com.timw.iprwc.services.DatabaseService;
import com.timw.iprwc.services.JacksonService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/debug")
public class DebugResource {

    @GET
    @Path("/tables")
    public String getTables() {
        String tables = "";

        try {
            String sql = "SELECT name FROM sqlite_master WHERE type='table';";
            PreparedStatement ps = DatabaseService.prepareQuery(sql);
            ResultSet rs = DatabaseService.executeQuery(ps);
            while(rs.next()) {
                String name = rs.getString("name");
                tables += name + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }

    @POST
    @Path("/product")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postProduct(String productData) {

        Product product = (Product) JacksonService.fromJson(productData, Product.class);

        String out = "";
        out += "product_id: " + product.productId + "\n";
        out += "name: " + product.name + "\n";
        out += "description: " + product.description + "\n";
        out += "price: " + product.price + "\n";

        return out;
    }

}
