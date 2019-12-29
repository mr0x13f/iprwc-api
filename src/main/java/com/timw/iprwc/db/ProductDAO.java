package com.timw.iprwc.db;

import com.timw.iprwc.models.Product;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Product> findAll() {
        return super.list((Query) namedQuery(
                "iprwc.Product.findAll"));
    }

}
