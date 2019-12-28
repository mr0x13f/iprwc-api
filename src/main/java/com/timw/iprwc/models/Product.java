package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Product {

    public String productId;
    public String name;
    public String description;
    public float price;

    @JsonCreator
    public Product(@JsonProperty("productId") String productId, // consume JSON field, but force random UUID
                   @JsonProperty("name") String name,
                   @JsonProperty("description") String description,
                   @JsonProperty("price") float price) {

        this.productId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
