package com.timw.iprwc.models;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "wishlist_items")
@NamedQueries({
        @NamedQuery(name = "iprwc.WishlistItem.findAll",
                query = "SELECT w FROM WishlistItem w"
                        + " LEFT JOIN Product p ON w.productId = p.productId"
                        + " WHERE w.userId = :userId"
                        + " ORDER BY p.name"),

        @NamedQuery(name = "iprwc.WishlistItem.findById",
                query = "SELECT w FROM WishlistItem w"
                        + " WHERE w.userId = :userId"
                        + " AND w.productId = :productId"),

        @NamedQuery(name = "iprwc.WishlistItem.delete",
                query = "DELETE FROM WishlistItem w"
                        + " WHERE w.userId = :userId"
                        + " AND w.productId = :productId"),

        @NamedQuery(name = "iprwc.WishlistItem.clear",
                query = "DELETE FROM WishlistItem w"
                        + " WHERE w.userId = :userId")
})
public class WishlistItem implements Serializable {

    @Id
    @Column(name = "user_id")
    public String userId;
    @Id
    @Column(name = "product_id")
    public String productId;

    public WishlistItem() {}

}
