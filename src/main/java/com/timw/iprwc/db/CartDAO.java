package com.timw.iprwc.db;

import com.timw.iprwc.models.CartItem;
import com.timw.iprwc.models.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CartDAO extends AbstractDAO<CartItem> {

    public CartDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<CartItem> findAll(User user) {
        return super.list((Query) namedQuery(
                "iprwc.CartItem.findAll")
                .setParameter("userId", user.userId));
    }

    public Optional<CartItem> findById(User user, String productId) {
        return Optional.ofNullable(uniqueResult((Query) namedQuery(
                "iprwc.CartItem.findById")
                .setParameter("userId", user.userId)
                .setParameter("productId", productId)));
    }

    public void delete(User user, String productId) {
        namedQuery(
                "iprwc.CartItem.delete")
                .setParameter("userId", user.userId)
                .setParameter("productId", productId)
                .executeUpdate();
    }

    public void clear(User user) {
        namedQuery(
                "iprwc.CartItem.clear")
                .setParameter("userId", user.userId)
                .executeUpdate();
    }

    public CartItem create(User user, CartItem cartItem) {

        cartItem.userId = user.userId;
        return persist(cartItem);

    }

}