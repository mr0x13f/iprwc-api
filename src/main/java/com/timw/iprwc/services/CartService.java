package com.timw.iprwc.services;

import com.timw.iprwc.db.CartDAO;
import com.timw.iprwc.db.OrderDAO;
import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.models.CartItem;
import com.timw.iprwc.models.Order;
import com.timw.iprwc.models.User;

import java.util.List;
import java.util.Optional;

public class CartService {

    private static CartDAO cartDAO;
    private static ProductDAO productDAO;
    private static OrderDAO orderDAO;

    public static void setDAO(CartDAO cartDAO, ProductDAO productDAO, OrderDAO orderDAO) {
        CartService.cartDAO = cartDAO;
        CartService.productDAO = productDAO;
        CartService.orderDAO = orderDAO;
    }

    public static Optional<CartItem> addToCart(User user, CartItem cartItem) {

        if (!productDAO.findById(cartItem.productId).isPresent())
            return Optional.empty(); // Don't allow invalid product ids

        cartItem = cartDAO.create(user, cartItem);

        return Optional.of(cartItem);

    }

    public static void checkout(User user) {

        List<CartItem> cartItemList = cartDAO.findAll(user);
        cartDAO.clear(user);

        for (CartItem cartItem : cartItemList) {
            Order order = new Order(cartItem);
            orderDAO.create(user, order);
        }

    }

}
