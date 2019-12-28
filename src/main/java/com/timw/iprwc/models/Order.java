package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class Order {

    public String orderId;
    public String userId;
    public String productId;
    public String date;
    public int amount;

    @JsonCreator
    public Order(@JsonProperty("orderId") String orderId, // consume JSON field, but force random UUID
                @JsonProperty("date") String date, // Consume JSON field, but enforce current date
                @JsonProperty("userId") String userId,
                @JsonProperty("productId") String productId,
                @JsonProperty("amount") int amount) {

        this.orderId = UUID.randomUUID().toString();
        this.date = OffsetDateTime.now( ZoneOffset.UTC ).toString();
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
    }


}
