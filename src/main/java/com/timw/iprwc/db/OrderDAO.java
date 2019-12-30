package com.timw.iprwc.db;

import com.timw.iprwc.models.Order;
import com.timw.iprwc.models.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class OrderDAO extends AbstractDAO<Order> {

    public OrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Order> findAll(User user) {
        return super.list((Query) namedQuery(
                "iprwc.Order.findAll")
                .setParameter("userId", user.userId));
    }

    public Optional<Order> findById(User user, String orderId) {
        return Optional.ofNullable(uniqueResult((Query) namedQuery(
                "iprwc.Order.findById")
                .setParameter("userId", user.userId)
                .setParameter("orderId", orderId)));
    }

    public Order create(User user, Order order) {

        order.userId = user.userId;
        return persist(order);

    }

}
