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
                query = "select w from WishlistItem w"
                        + " left join Product p on w.productId = p.productId"
                        + " where w.userId = :userId"
                        + " ordery by p.name"),

        @NamedQuery(name = "iprwc.WishlistItem.findById",
                query = "select w from WishlistItem w"
                        + " where w.userId = :userId"
                        + " and w.productId = :productId"),

        @NamedQuery(name = "iprwc.WishlistItem.delete",
                query = "delete from WishlistItem w"
                        + " where w.userId = :userId"
                        + " and w.productId = :productId"),

        @NamedQuery(name = "iprwc.WishlistItem.clear",
                query = "delete from WishlistItem w"
                        + " where w.userId = :userId")
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
