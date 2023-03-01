package com.estore.api.estoreapi.model;

import java.util.HashMap;
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
    @JsonProperty("id") private int id;
    // Maps product id to quantity
    @JsonProperty("inventory") private HashMap<Integer, ProductReference> inventory;

    /**
     * Constructor for the cart, creates an empty cart
     */
    public Cart(@JsonProperty("id") int id) {
        this.id = id;
        this.inventory = new HashMap<>();
    }

    /**
     * Get the inventory of the cart
     * 
     * @return Inventory of the cart
     */
    public HashMap<Integer, ProductReference> getInventory() {
        return inventory;
    }

    /**
     * Adds a product to the cart by calling the ProductReference add method
     * 
     * Checks to see if it already exists and if we should just add on to the quantity
     * 
     * @param productId Id of the product to add
     * @param quantity Quantity of the product to add
     */
    public void addItem(int productId, int quantity) {
        if (inventory.containsKey(productId)) {
            int currentQuantity = inventory.get(productId).getQuantity();
            inventory.get(productId).setQuantity(currentQuantity + quantity);
        }
        else {
            inventory.put(productId, new ProductReference(productId, quantity));
        }
    }

    /**
     * Edit the quantity of a product reference in the inventory
     * 
     * @param productId Id of the product to edit
     * @param quantity Quantity to set the product to, 0 will remove it
     */
    public void editQuantity(int productId, int quantity) {
        if (inventory.containsKey(productId)) {
            if (quantity == 0) {
                inventory.remove(productId);
            }
            else {
                inventory.get(productId).setQuantity(quantity);
            }
        }
    }

    /**
     * Remove an item from the cart
     * 
     * @param productId Id of the product to remove
     */
    public void removeItem(int productId) {
        editQuantity(productId, 0);
    }

    /**
     * Clears a cart, used when a user checks out
     */
    public void clear() {
        inventory.clear();
    }

    /**
     * Get the id of the cart
     * 
     * @return Id of the cart
     */
    public int getId() {
        return id;
    }
}
