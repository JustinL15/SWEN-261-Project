package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Review File DAO class
 * 
 * @author Alexandria Pross
 */
public class ReviewFileDAOTest {
    ReviewFileDAO reviewFileDAO;
    Review[] testReviews;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupReviewFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testReviews = new Review[3];
        testReviews[0] = new Review(99, 88, 77, 5, "good product", "good to know");
        testReviews[1] = new Review(100, 99, 88, 4, "okay product", "cool");
        testReviews[2] = new Review(111, 100, 99, 2, "pretty mid", "not cool");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the review array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Review[].class))
                .thenReturn(testReviews);
        reviewFileDAO = new ReviewFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetReviews() {
        // Invoke
        Review[] reviews = reviewFileDAO.getReviews();

        // Analyze
        assertEquals(reviews.length,testReviews.length);
        for (int i = 0; i < testReviews.length;++i)
            assertEquals(reviews[i],testReviews[i]);
    }

    @Test
    public void testGetReview() {
        // Invoke
        Review review = reviewFileDAO.getReview(99);

        // Analzye
        assertEquals(review,testReviews[0]);
    }

    @Test
    public void testDeleteReview() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> reviewFileDAO.deleteReview(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test review array - 1 (because of the delete)
        // Because reviews attribute of ReviewFileDAO is package private
        // we can access it directly
        assertEquals(reviewFileDAO.reviewMap.size(),testReviews.length-1);
    }

   // @Test
    //public void testCreateReview() {
        // Setup
      //  Review review = new Review(90,80, 70, 3, "decent", "comment");

        // Invoke
      //  Review result = assertDoesNotThrow(() -> reviewFileDAO.createReview(review),
      //                          "Unexpected exception thrown");

        // Analyze
    //    assertNotNull(result);
   //     Review actual = reviewFileDAO.getReview(review.getId());
   //     assertEquals(actual.getId(), review.getId());
   //     assertEquals(actual.getReviewContent(),review.getReviewContent());
  //  }

    @Test
    public void testUpdateReview() {
        // Setup
        Review review = new Review(99, 88, 77, 2, "cool", "creative comment");

        // Invoke
        Review result = assertDoesNotThrow(() -> reviewFileDAO.updateReview(review),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Review actual = reviewFileDAO.getReview(review.getId());
        assertEquals(actual,review);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Review[].class));

        Review review = new Review(99, 88, 77, 2, "review", "comment");

        assertThrows(IOException.class,
                        () -> reviewFileDAO.createReview(review),
                        "IOException not thrown");
    }

    @Test
    public void testGetReviewNotFound() {
        // Invoke
        Review review = reviewFileDAO.getReview(20);

        // Analyze
        assertEquals(review,null);
    }

    @Test
    public void testDeleteReviewNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> reviewFileDAO.deleteReview(20),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(reviewFileDAO.reviewMap.size(),testReviews.length);
    }

    @Test
    public void testUpdateReviewNotFound() {
        // Setup
        Review review = new Review(20, 30, 40, 1, "review string", "comment string");

        // Invoke
        Review result = assertDoesNotThrow(() -> reviewFileDAO.updateReview(review),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the ProductFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Review[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ReviewFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }

}
