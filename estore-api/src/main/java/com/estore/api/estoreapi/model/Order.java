package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    /// Setup all parameters that the product should have
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("products") private String products;

    /**
     * Constructor for the orders
     * 
     * @param id ID of the order
     * @param name Name on the order
     * @param price Price of the order
     * @param products list of products
     */
    public Order(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") double price,
                    @JsonProperty("products") String products) {
        this.id = id;
        this.name = name;
        this.price = price;
        if (products == null) {
            this.products = "";
        }
        else {
            this.products = products;
        }
    }

    /**
     * Get the ID of the order
     * 
     * @return ID of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the order
     * 
     * @param id ID of the order
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name on the order
     * 
     * @return Name on the order
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name on the order
     * 
     * @param name Name on the order
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the price of the products
     * 
     * @return Price of the products
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the products
     * 
     * @param price Price of the products
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the products in the order
     * 
     * @return Products in the order
     */
    public String getProducts() {
        return products;
    }

    /**
     * Set the products in the order
     * 
     * @param quantity Products in the order
     */
    public void setProducts(String products) {
        this.products = products;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Product [order id=" + id + ", customer name=" + name + ", products=" + products + ", total price=" + price + "]";
    }
    
}
