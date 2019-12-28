package com.timw.iprwc.resources;

import com.timw.iprwc.services.DatabaseService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
