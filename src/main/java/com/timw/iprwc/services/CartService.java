package com.timw.iprwc.services;

import com.timw.iprwc.db.CartDAO;
import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.models.CartItem;
import com.timw.iprwc.models.User;

import java.util.List;
import java.util.Optional;

public class CartService {

    private static CartDAO cartDAO;
    private static ProductDAO productDAO;

    public static void setDAO(CartDAO cartDAO, ProductDAO productDAO) {
        CartService.cartDAO = cartDAO;
        CartService.productDAO = productDAO;
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

        //TODO create orders

    }

}
