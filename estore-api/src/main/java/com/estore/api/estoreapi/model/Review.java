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
    @JsonProperty("customerId") private int customerId;
    @JsonProperty("stars") private float stars;
    @JsonProperty("reviewContent") private String reviewContent;

    /**
     * Constructor for the reviews
     * 
     * @param id ID of the review
     * @param productId ID of the product
     * @param customerId customer making the review
     * @param stars rate given in the review
     * @param reviewContent body of the review
     */
    public Review(@JsonProperty("id") int id, @JsonProperty("productId") int productId, 
                @JsonProperty("customerId") int customerId,
                @JsonProperty("stars") float stars, @JsonProperty("reviewContent") String reviewContent) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.stars = stars;
        
        if (reviewContent == null) {
            this.reviewContent = "";
        }
        else {
            this.reviewContent = reviewContent;
        }

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
     * Get the customer id
     * 
     * @return the customer id
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Set the customer's id
     * 
     * @return the customer's id
     */
    public int setCustomerId() {
        return this.customerId;
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
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Review) {
            Review other = (Review) obj;
            return id == other.id && productId == other.productId && stars == other.stars 
                            && other.reviewContent == reviewContent;
        }
        return false;
    }
    
}
