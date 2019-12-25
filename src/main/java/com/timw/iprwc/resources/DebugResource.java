package com.timw.iprwc.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/debug")
public class DebugResource {

    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public String GETDebug() {
        return "epic";
    }

}
