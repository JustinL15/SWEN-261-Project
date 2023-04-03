package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model for a product in the store
 * 
 * @author Alexandria Pross
 */

public class Review {
   
    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    /// Setup all parameters that the review should have
    @JsonProperty("id") private int id;
    @JsonProperty("productId") private int productId;
    @JsonProperty("customer") private Customer customerUser;
    @JsonProperty("stars") private float stars;
    @JsonProperty("reviewContent") private String reviewContent;
    @JsonProperty("time") private LocalDateTime dateTime;
    @JsonProperty("username") private String username;
    @JsonProperty("purchased") private List<Integer> purchased;

    /**
     * Constructor for the reviews
     * 
     * @param id ID of the review
     * @param customer customer making the review
     * @param stars stars given in the review
     * @param reviewContent body of the review
     * @param dateTime the date and time of the order
     */
    public Review(@JsonProperty("id") int id, @JsonProperty("productId") int productId, 
                @JsonProperty("customer") Customer customerUser,
                @JsonProperty("stars") float stars, @JsonProperty("reviewContent") String reviewContent,
                @JsonProperty("time") LocalDateTime dateTime, @JsonProperty("username") String username,
                @JsonProperty("purchased") List<Integer> purchased) {
        this.id = id;
        this.productId = productId;
        this.customerUser = customerUser;
        this.stars = stars;
        
        if (reviewContent == null) {
            this.reviewContent = "";
        }
        else {
            this.reviewContent = reviewContent;
        }

        if (dateTime == null) {
            this.dateTime = LocalDateTime.now();
       }
       else {
            this.dateTime = dateTime;
       }

       this.username = customerUser.getUsername();
       this.purchased = customerUser.getPurchasedProducts();
    }

    /**
     * Get the ID of the review
     * 
     * @return ID of the review
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the review
     * 
     * @param id ID of the review
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the ID of the product
     * 
     * @return ID of the product
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Set the ID of the product
     * 
     * @param productId ID of the product
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Get the customer username
     * 
     * @return the customer username
     */
    public String getCustomerUsername() {
        return this.username;
    }

    /**
     * Get the customer's previous orders
     * 
     * @return the customer's orders
     */
    public List<Integer> getProducts() {
        return this.purchased;
    }

    /**
     * Get the customer's admin status
     * 
     * @return the customer's admin status
     */
    public boolean isAdmin() {
        return customerUser.isAdmin();
    }

    /**
     * Get the customer
     * 
     * @return the customer
     */
    public Customer getCustomerUser() {
        return customerUser;
    }

    /**
     * Set the cusomer on the review
     * 
     * @param customer customer on the review
     */
    public void setCustomer(Customer customerUser) {
        this.customerUser = customerUser;
    }

    /**
     * Get the stars on the review
     * 
     * @return stars
     */
    public float getStars() {
        return stars;
    }

    /**
     * Set the stars on the review
     * 
     * @param stars the stars on the review
     */
    public void setStars(float stars) {
        this.stars = stars;
    }

     /**
     * Get the body of the review
     * 
     * @return body of the review
     */
    public String getReviewContent() {
        return reviewContent;
    }

    /**
     * Set the body of the review
     * 
     * @param reviewContent body of the review
     */
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
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
        if (obj instanceof Review) {
            Review other = (Review) obj;
            return id == other.id && productId == other.productId && stars == other.stars
                            && dateTime == other.dateTime && other.reviewContent == reviewContent && 
                            customerUser == other.customerUser;
        }
        return false;
    }

    public boolean purchase(Order order) {
        return true;
    }
    
}
