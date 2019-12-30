package com.timw.iprwc.services;

import com.timw.iprwc.db.ProductDAO;
import com.timw.iprwc.db.WishlistDAO;
import com.timw.iprwc.models.User;
import com.timw.iprwc.models.WishlistItem;

import java.util.Optional;

public class WishlistService {

    private static WishlistDAO wishlistDAO;
    private static ProductDAO productDAO;

    public static void setDAO(WishlistDAO wishlistDAO, ProductDAO productDAO) {
        WishlistService.wishlistDAO = wishlistDAO;
        WishlistService.productDAO = productDAO;

    }

    public static Optional<WishlistItem> addToWishlist(User user, WishlistItem wishlistItem) {

        if (!productDAO.findById(wishlistItem.productId).isPresent())
            return Optional.empty(); // Don't allow invalid product ids

        wishlistItem = wishlistDAO.create(user, wishlistItem);

        return Optional.of(wishlistItem);

    }

}
