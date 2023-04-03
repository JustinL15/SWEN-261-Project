package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for a product in the store
 * 
 * @author Matt London
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    /// Setup all parameters that the product should have
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    @JsonProperty("description") private String description;
    @JsonProperty("categories") private List<String> categories;
    @JsonProperty("ownerRecommended") private boolean ownerRecommended;

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
                    @JsonProperty("quantity") int quantity, @JsonProperty("description") String description,
                    @JsonProperty("categories") List<String> categories,
                    @JsonProperty("ownerRecommended") boolean ownerRecommended) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        if (description == null) {
            this.description = "";
        }
        else {
            this.description = description;
        }
        if (categories == null) {
            this.categories = new ArrayList<String>();
        }
        else {
            this.categories = categories;
        }
        this.ownerRecommended = ownerRecommended;
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
     * Get the categories of the product
     * 
     * @return Categories
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Get the owner recommended status of the product
     * 
     * @return Owner recommended status of the product
     */
    public boolean isOwnerRecommended() {
        return ownerRecommended;
    }

    /**
     * Set the owner recommended status of the product
     * 
     * @param ownerRecommended Owner recommended status of the product
     */
    public void setOwnerRecommended(boolean ownerRecommended) {
        this.ownerRecommended = ownerRecommended;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", quantity=" + quantity
                + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product other = (Product) obj;
            return this.id == other.id && this.name.equals(other.name) && this.description.equals(other.description)
                    && this.price == other.price && this.quantity == other.quantity;
        }

        return false;
    }

}
