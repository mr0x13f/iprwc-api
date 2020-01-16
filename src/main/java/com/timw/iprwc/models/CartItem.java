package com.timw.iprwc.models;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cart_items")
@NamedQueries({
        @NamedQuery(name = "iprwc.CartItem.findAll",
                query = "SELECT c FROM CartItem c"
                        + " LEFT JOIN Product p ON c.productId = p.productId"
                        + " WHERE c.userId = :userId"
                        + " ORDER BY p.name"),

        @NamedQuery(name = "iprwc.CartItem.findById",
                query = "SELECT c FROM CartItem c"
                        + " WHERE c.userId = :userId"
                        + " AND c.productId = :productId"),

        @NamedQuery(name = "iprwc.CartItem.delete",
                query = "DELETE FROM CartItem c"
                        + " WHERE c.userId = :userId"
                        + " AND c.productId = :productId"),

        @NamedQuery(name = "iprwc.CartItem.clear",
                query = "DELETE FROM CartItem c"
                        + " WHERE c.userId = :userId")
})
public class CartItem implements Serializable {

    @Id
    @Column(name = "user_id")
    public String userId;
    @Id
    @Column(name = "product_id")
    public String productId;
    public int amount;

    public CartItem() {}

}
