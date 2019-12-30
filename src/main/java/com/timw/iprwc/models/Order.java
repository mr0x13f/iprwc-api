package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(name = "iprwc.Order.findAll",
                query = "select o from Order o"
                        + " where o.userId = :userId"),

        @NamedQuery(name = "iprwc.Order.findById",
                query = "select o from Order o"
                        + " where o.userId = :userId"
                        + " and o.orderId = :orderId")
})
public class Order {

    @Id
    @Column(name = "order_id")
    public String orderId;
    @Column(name = "user_id")
    public String userId;
    @Column(name = "product_id")
    public String productId;
    public String date;
    public int amount;

    public Order() {}

    public Order(CartItem cartItem) {

        this.userId = cartItem.userId;
        this.productId = cartItem.productId;
        this.amount = cartItem.amount;

        this.orderId = UUID.randomUUID().toString();
        this.date = OffsetDateTime.now( ZoneOffset.UTC ).toString();
    }


}
