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
                query = "select c from CartItem c"
                        + " left join Product p on c.productId = p.productId"
                        + " where c.userId = :userId"
                        + " order by p.name"),

        @NamedQuery(name = "iprwc.CartItem.findById",
                query = "select c from CartItem c"
                        + " where c.userId = :userId"
                        + " and c.productId = :productId"),

        @NamedQuery(name = "iprwc.CartItem.delete",
                query = "delete from CartItem c"
                        + " where c.userId = :userId"
                        + " and c.productId = :productId"),

        @NamedQuery(name = "iprwc.CartItem.clear",
                query = "delete from CartItem c"
                        + " where c.userId = :userId")
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
