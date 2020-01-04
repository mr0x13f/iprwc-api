package com.timw.iprwc.resources;

import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.db.UserDAO;
import com.timw.iprwc.models.BasicAuth;
import com.timw.iprwc.models.LoginResponse;
import com.timw.iprwc.models.Product;
import com.timw.iprwc.models.User;
import com.timw.iprwc.services.JacksonService;
import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.hibernate.UnitOfWork;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

@Path("/debug")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DebugResource {

    private UserDAO userDAO;
    private ProductDAO productDAO;

    public DebugResource(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @POST
    @Path("/product")
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

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    @UnitOfWork
    public String adminTest(@Auth User user) {
        return "hello mr admin :)";
    }

    @GET
    @Path("/token")
    @UnitOfWork
    public LoginResponse token(@Auth BasicAuth basicAuth) throws JoseException {
        return new LoginResponse(buildToken(basicAuth.user).getCompactSerialization());
    }

    private JsonWebSignature buildToken(User user) {
        // These claims would be tightened up for production
        final JwtClaims claims = new JwtClaims();
        claims.setSubject("1");
        claims.setStringClaim("userId", user.userId);
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(HMAC_SHA256);
        jws.setKey(new HmacKey("funny".getBytes()));
        return jws;
    }

}
