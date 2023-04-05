package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Review model
 * 
 * @author Alexandria Pross
 */
public class ReviewTest {

    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        int expected_product_id = 88;
        int expected_customer_id = 77;
        int expected_stars = 3;
        String expected_review_content = "cool product";
        String expected_owner_response = "thank you";


        // Invoke
        Review review = new Review(expected_id,expected_product_id,expected_customer_id,expected_stars,expected_review_content,expected_owner_response);
        review.setId(expected_id);
        review.setProductId(expected_product_id);
        review.setCustomerId(expected_customer_id);


        // Analyze
        assertEquals(expected_id,review.getId());
        assertEquals(expected_product_id,review.getProductId());
        assertEquals(expected_customer_id,review.getCustomerId());
        assertEquals(expected_stars,review.getStars());
        assertEquals(expected_review_content,review.getReviewContent());
        assertEquals(expected_owner_response,review.getOwnerResponse());
    }

    @Test
    public void testCtor2() {
        // Setup
        int expected_id = 99;
        int expected_product_id = 88;
        int expected_customer_id = 77;
        int expected_stars = 3;
        String expected_review_content = null;
        String expected_owner_response = null;


        // Invoke
        Review review = new Review(expected_id,expected_product_id,expected_customer_id,expected_stars,expected_review_content,expected_owner_response);
        review.setId(expected_id);
        review.setProductId(expected_product_id);
        review.setCustomerId(expected_customer_id);


        // Analyze
        assertEquals(expected_id,review.getId());
        assertEquals(expected_product_id,review.getProductId());
        assertEquals(expected_customer_id,review.getCustomerId());
        assertEquals(expected_stars,review.getStars());
    }
    
    @Test
    public void testReviewContent() {
        // Setup
        int id = 99;
        int product_id = 88;
        int customer_id = 77;
        int stars = 3;
        String review_content = "cool product";
        String owner_response = "thank you";
        Review review = new Review(id,product_id,customer_id,stars,review_content,owner_response);

        String expected_review_content = "not a cool product";

        // Invoke
        review.setReviewContent(expected_review_content);

        // Analyze
        assertEquals(expected_review_content,review.getReviewContent());
    }

    @Test
    public void testOwnerResponse() {
        // Setup
        int id = 99;
        int product_id = 88;
        int customer_id = 77;
        int stars = 3;
        String review_content = "cool product";
        String owner_response = "thank you";
        Review review = new Review(id,product_id,customer_id,stars,review_content,owner_response);

        String expected_owner_response = "thats not nice";

        // Invoke
        review.setOwnerResponse(expected_owner_response);

        // Analyze
        assertEquals(expected_owner_response,review.getOwnerResponse());
    }

    @Test
    public void testStars() {
        // Setup
        int id = 99;
        int product_id = 88;
        int customer_id = 77;
        int stars = 3;
        String review_content = "cool product";
        String owner_response = "thank you";
        Review review = new Review(id,product_id,customer_id,stars,review_content,owner_response);

        float expected_stars = 2;

        // Invoke
        review.setStars(expected_stars);

        // Analyze
        assertEquals(expected_stars,review.getStars());
    }

    @Test
    public void testEqualsFalse() {
        Review review = new Review(99,88,77,3,"cool product","thank you");

        Review id = new Review(98,88,77,3,"cool product","thank you");
        assertFalse(review.equals(id));

        Review productId = new Review(99,87,77,3,"cool product","thank you");
        assertFalse(review.equals(productId));

        Review customerId = new Review(99,88,8,3,"cool product","thank you");
        assertFalse(review.equals(customerId));
       
        Review stars = new Review(99,88,77,2,"cool product","thank you");
        assertFalse(review.equals(stars));

        Review reviewContent = new Review(99,88,77,3,"not a cool product","thank you");
        assertFalse(review.equals(reviewContent));

        Review ownerResponse = new Review(99,88,77,3,"cool product"," nothank you");
        assertFalse(review.equals(ownerResponse));

    }

}
