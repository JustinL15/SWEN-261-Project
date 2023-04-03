package com.estore.api.estoreapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for the backend Customer
 * 
 * @author Matt London
 */
public class Customer {
    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

    // Set all customer parameters
    @JsonProperty("id") private int id;
    @JsonProperty("username") private String username;
    @JsonProperty("name") private String name;
    @JsonProperty("cartId") private int cartId;
    @JsonProperty("orders") private List<Integer> orders;
    @JsonProperty("isAdmin") private boolean isAdmin;
    @JsonProperty("password") private String password;
    @JsonProperty("products") private List<Integer> products;

    /**
     * Construct a new customer
     * 
     * @param id ID of the customer
     * @param username Username of the customer (must be unique, checked by DAO)
     * @param name Name of the customer
     * @param cartId ID of the customer's cart
     * @param isAdmin Whether the customer is an admin
     * @param password Password of the customer
     */
    public Customer(@JsonProperty("id") int id, @JsonProperty("username") String username,
                    @JsonProperty("name") String name, @JsonProperty("cartId") int cartId,
                    @JsonProperty("isAdmin") boolean isAdmin, @JsonProperty("password") String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.cartId = cartId;
        this.isAdmin = isAdmin;
        this.password = password;
        this.orders = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    /**
     * Get the ID of the customer
     * 
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of customer
     * 
     * @param id customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the username of the customer
     * 
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of customer
     * 
     * @param username customer username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the user's password. Only for instantiating a user, immediately clears the password
     * 
     * @return Password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of customer
     * 
     * @param password customer password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the name of the customer
     * 
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of customer
     * 
     * @param name customer name
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     * Get the purchased products
     * 
     * @return products
     */
    public List<Integer> getPurchasedProducts() {
        return products;
    }

    /**
     * Set the purchased products
     * 
     * @param productId customer productsIds
     */
    public void addPurchasedProduct(int productId) {
        products.add(productId);
    }

    /**
     * Get the cart ID of the customer
     * 
     * @return Cart ID
     */
    public int getCartId() {
        return cartId;
    }

    /**
     * Set the cart ID of customer
     * 
     * @param cartId customer cart ID
     */
    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    /**
     * Get the orders of the customer
     * 
     * @return Orders
     */
    public List<Integer> getOrders() {
        return orders;
    }

    /**
     * Add orders to the customer
     * 
     * @param orderId OrderID to add
     */
    public void addOrder(int orderId) {
        orders.add(orderId);
    }

    

    /**
     * Check if the customer is an admin
     * 
     * @return True if admin, false otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }



}
