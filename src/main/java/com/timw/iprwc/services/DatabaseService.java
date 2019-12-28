package com.timw.iprwc.services;

import java.sql.*;

public class DatabaseService {

    private static String databaseUrl;
    private static String conUrl = "jdbc:sqlite:";
    private static Connection con;

    private static Connection getCon() {
        if (con != null) return con;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(conUrl + databaseUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return con;
    }

    public static void setDatabaseUrl(String newDatabaseUrl) {
        databaseUrl = newDatabaseUrl;
        System.out.println(newDatabaseUrl);
    }

    public static PreparedStatement prepareQuery(String query) {
        PreparedStatement ps = null;
        try {
            ps = getCon().prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static ResultSet executeQuery(PreparedStatement ps) {
        ResultSet rs = null;
        try {
            ps.execute();
            rs = ps.getResultSet();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

}
