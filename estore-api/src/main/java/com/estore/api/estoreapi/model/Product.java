package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for a product in the store
 * 
 * @author Matt London
 * @author ADD NAMES HERE
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    /// Setup all parameters that the product should have
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("description") private String description;

    /**
     * Constructor for the product
     * 
     * @param id ID of the product
     * @param name Name of the product
     * @param price Price of the product
     * @param quantity Quantity of the product
     * @param description Description for the product
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price,
                    @JsonProperty("quantity") int quantity, @JsonProperty("description") String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    /**
     * Get the ID of the product
     * 
     * @return ID of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the product
     * 
     * @param id ID of the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name of the product
     * 
     * @return Name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the product
     * 
     * @param name Name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the price of the product
     * 
     * @return Price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the product
     * 
     * @param price Price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the quantity of the product
     * 
     * @return Quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the product
     * 
     * @param quantity Quantity of the product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the description of the product
     * 
     * @return Description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the product
     * 
     * @param description Description of the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", quantity=" + quantity
                + "]";
    }

}
