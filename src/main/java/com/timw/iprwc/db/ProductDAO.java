package com.timw.iprwc.db;

import com.timw.iprwc.models.Product;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Product> findAll() {
        return super.list((Query) namedQuery(
                "iprwc.Product.findAll"));
    }

    public Optional<Product> findById(String productId) {
        return Optional.ofNullable(uniqueResult((Query) namedQuery(
                "iprwc.Product.findById")
                .setParameter("productId", productId)));
    }

    public void delete(String productId) {
        namedQuery(
                "iprwc.Product.delete")
                .setParameter("productId", productId)
                .executeUpdate();
    }

    public Product create(Product product) {

        return persist(product);

    }

    public Product update(String productId, Product product) {

        product.productId = productId;
        return persist(product);

    }


}
