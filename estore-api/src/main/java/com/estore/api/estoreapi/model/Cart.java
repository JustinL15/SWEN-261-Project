package com.estore.api.estoreapi.model;


import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for a cart that will be owned by a customer
 * 
 * @author Matt London
 */
public class Cart {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // All cart parameters
    @JsonProperty("inventory") private List<ProductReference> inventory;
    @JsonProperty("total") private double total;

    /**
     * Constructor for the cart, creates an empty cart
     */
    public Cart() {
        this.inventory = new ArrayList<ProductReference>();
        this.total = 0;
    }

    /**
     * Get the inventory of the cart
     * 
     * @return Inventory of the cart
     */
    public List<ProductReference> getInventory() {
        return inventory;
    }

    /**
     * Adds a product to the cart
     * 
     * @param item Product reference to add
     */
    public void addItem(ProductReference item) {
        inventory.add(item);
        // TODO reference the productDAO so that we can get the price
        // TODO reference the productDAO so we can subtract from quantity
    }

    /**
     * Adds a product to the cart by calling the ProductReference add method
     * 
     * @param productId Id of the product to add
     * @param quantity Quantity of the product to add
     */
    public void addItem(int productId, int quantity) {
        inventory.add(new ProductReference(productId, quantity));
    }

    /**
     * Clears a cart, used when a user checks out
     */
    public void clear() {
        inventory.clear();
        total = 0;
    }
}
