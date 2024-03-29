package com.timw.iprwc.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "iprwc.Product.findAll",
            query = "SELECT p FROM Product p"
                + " ORDER BY p.name"),

        @NamedQuery(name = "iprwc.Product.findById",
            query = "SELECT p FROM Product p"
                + " WHERE p.productId = :productId"),

        @NamedQuery(name = "iprwc.Product.delete",
            query = "DELETE FROM Product p"
                + " WHERE p.productId = :productId")
})
public class Product {

    @Id
    @Column(name = "product_id")
    public String productId;
    public String name;
    public String description;
    @Column(name = "image_url")
    public String imageUrl;
    public float price;

    public Product() {}

    @JsonCreator
    public Product(@JsonProperty("productId") String productId, // consume JSON field, but force random UUID
                   @JsonProperty("name") String name,
                   @JsonProperty("description") String description,
                   @JsonProperty("imageUrl") String imageUrl,
                   @JsonProperty("price") float price) {

        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;

        this.productId = UUID.randomUUID().toString();
    }

}
