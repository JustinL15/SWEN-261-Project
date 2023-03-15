package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Model for a order in the store
 * 
 * @author Alexandria Pross
 */

public class Order {
    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    /// Setup all parameters that the product should have
    @JsonProperty("id") private int id;
    @JsonProperty("totalPrice") private double totalPrice;
    @JsonProperty("products") private Product[] products;
    @JsonProperty("complete") private boolean complete;
    @JsonProperty("time") private LocalDateTime dateTime;

    /**
     * Constructor for the orders
     * 
     * @param id ID of the order
     * @param price Price of the order
     * @param products list of products
     * @param dateTime the date and time of the order
     */
    public Order(@JsonProperty("id") int id, @JsonProperty("totalPrice") double totalPrice,
                @JsonProperty("products") Product[] products, @JsonProperty("complete") boolean complete,
                @JsonProperty("time") LocalDateTime dateTime) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.products = products;
        this.complete = complete;

        if (dateTime == null) {
            this.dateTime = LocalDateTime.now();
       }
       else {
            this.dateTime = dateTime;
       }
    }

    /**
     * Get the status of the order
     * 
     * @return status of the order
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Set the status of the order
     * 
     * @param complete status of the order
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
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
     * Get the price of the products
     * 
     * @return Price of the products
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Set the price of the products
     * 
     * @param price Price of the products
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Get the products in the order
     * 
     * @return Products in the order
     */
    public Product[] getProducts() {
        return products;
    }

    /**
     * Set the products in the order
     * 
     * @param quantity Products in the order
     */
    public void setProducts(Product[] products) {
        this.products = products;
    }

    /**
     * Get the date and time of the order
     * 
     * @return date and time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order) {
            Order other = (Order) obj;

            // Compare trivial fields first
            boolean trivial = id == other.id && totalPrice == other.totalPrice
                            && dateTime == other.dateTime && other.complete == complete;

            // Compare products
            if (products.length == other.products.length) {
                for (int i = 0; i < products.length; i++) {
                    if (!products[i].equals(other.products[i])) {
                        return false;
                    }
                }
            }
            else {
                return false;
            }

            return trivial;
        }
        return false;
    }
    
}
