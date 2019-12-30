package com.timw.iprwc.db;

import com.timw.iprwc.models.User;
import com.timw.iprwc.models.WishlistItem;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class WishlistDAO extends AbstractDAO<WishlistItem> {

    public WishlistDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    public List<WishlistItem> findAll(User user) {
        return super.list((Query) namedQuery(
                "iprwc.WishlistItem.findAll")
                .setParameter("userId", user.userId));
    }

    public Optional<WishlistItem> findById(User user, String productId) {
        return Optional.ofNullable(uniqueResult((Query) namedQuery(
                "iprwc.WishlistItem.findById")
                .setParameter("userId", user.userId)
                .setParameter("productId", productId)));
    }

    public void delete(User user, String productId) {
        namedQuery(
                "iprwc.WishlistItem.delete")
                .setParameter("userId", user.userId)
                .setParameter("productId", productId)
                .executeUpdate();
    }

    public void clear(User user) {
        namedQuery(
                "iprwc.WishlistItem.clear")
                .setParameter("userId", user.userId)
                .executeUpdate();
    }

    public WishlistItem create(User user, WishlistItem wishlistitem) {

        wishlistitem.userId = user.userId;
        return persist(wishlistitem);

    }

}
