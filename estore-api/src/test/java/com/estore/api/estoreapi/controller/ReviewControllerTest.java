package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.ReviewDAO;
import com.estore.api.estoreapi.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Review Controller class
 * 
 * @author Alexandria Pross
 */
public class ReviewControllerTest {
    private ReviewController reviewController;
    private ReviewDAO mockReviewDAO;

    /**
     * Before each test, create a new ReviewController object and inject
     * a mock Reveiw DAO
     */
    @BeforeEach
    public void setupReviewController() {
        mockReviewDAO = mock(ReviewDAO.class);
        reviewController = new ReviewController(mockReviewDAO);
    }

    @Test
    public void testGetReview() throws IOException {  // getReview may throw IOException
        // Setup
        Review review = new Review(99,88,77,2, "good product", "good to know");
        // When the same id is passed in, our mock Review DAO will return the Review object
        when(mockReviewDAO.getReview(review.getId())).thenReturn(review);

        // Invoke
        ResponseEntity<Review> response = reviewController.getReview(review.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(review,response.getBody());
    }

    @Test
    public void testGetReviewNotFound() throws Exception { // createReview may throw IOException
        // Setup
        int reviewId = 99;
        // When the same id is passed in, our mock Review DAO will return null, simulating
        // no review found
        when(mockReviewDAO.getReview(reviewId)).thenReturn(null);

        // Invoke
        ResponseEntity<Review> response = reviewController.getReview(reviewId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetReviewHandleException() throws Exception { // createReview may throw IOException
        // Setup
        int reviewId = 99;
        // When getReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).getReview(reviewId);

        // Invoke
        ResponseEntity<Review> response = reviewController.getReview(reviewId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all ReviewController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateReview() throws IOException {  // createReview may throw IOException
        // Setup
        Review review = new Review(99,88,77,2, "good product", "good to know");
        // when createReview is called, return true simulating successful
        // creation and save
        when(mockReviewDAO.createReview(review)).thenReturn(review);

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(review,response.getBody());
    }
    
    @Test
    public void testCreateReviewFailed() throws IOException {  // createReview may throw IOException
        // Setup
        Review review = new Review(99,88,77,2, "good product", "good to know");
        // when createReview is called, return false simulating failed
        // creation and save
        when(mockReviewDAO.createReview(review)).thenReturn(null);
        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateReviewHandleException() throws IOException {  // createReview may throw IOException
        // Setup
        Review review = new Review(99,88,77,2, "good product", "good to know");

        // When createReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).createReview(review);

        // Invoke
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateReview() throws IOException { // updateReview may throw IOException
        // Setup
        Review review = new Review(99,88,77,2, "good product", "good to know");
        // when updateReview is called, return true simulating successful
        // update and save
        when(mockReviewDAO.updateReview(review)).thenReturn(review);
        ResponseEntity<Review> response = reviewController.updateReview(review);
        review.setReviewContent("bad product");

        // Invoke
        response = reviewController.updateReview(review);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(review,response.getBody());
    }

    @Test
    public void testUpdateReviewFailed() throws IOException { // updateReview may throw IOException
        // Setup
        Review review = new Review(99,88,77,2, "good product", "good to know");
        // when updateReview is called, return true simulating successful
        // update and save
        when(mockReviewDAO.updateReview(review)).thenReturn(null);
        

        // Invoke
        ResponseEntity<Review> response = reviewController.updateReview(review);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateReviewHandleException() throws IOException { // updateReview may throw IOException
        // Setup
        Review review = new Review(99,88,77,2, "good product", "good to know");
        // When updateReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).updateReview(review);

        // Invoke
        ResponseEntity<Review> response = reviewController.updateReview(review);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetReviews() throws IOException { // getReviews may throw IOException
        // Setup
        Review[] reviews = new Review[2];
        reviews[0] = new Review(99,88,77,2, "good product", "good to know");
        reviews[1] = new Review(98,87,76,1, "bad product", "sorry");
        // When getReviews is called return the reviews created above
        when(mockReviewDAO.getReviews()).thenReturn(reviews);

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviews();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(reviews,response.getBody());
    }

    @Test
    public void testGetReviewsNotFound() throws IOException { // getReviews may throw IOException
        when(mockReviewDAO.getReviews()).thenReturn(null);

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviews();

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetReviewsHandleException() throws IOException { // getReviews may throw IOException
        // Setup
        // When getReviews is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).getReviews();

        // Invoke
        ResponseEntity<Review[]> response = reviewController.getReviews();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteReview() throws IOException { // deleteReview may throw IOException
        // Setup
        int reviewId = 99;
        // when deleteReview is called return true, simulating successful deletion
        when(mockReviewDAO.deleteReview(reviewId)).thenReturn(true);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(reviewId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteReviewNotFound() throws IOException { // deleteReview may throw IOException
        // Setup
        int reviewId = 99;
        // when deleteReview is called return false, simulating failed deletion
        when(mockReviewDAO.deleteReview(reviewId)).thenReturn(false);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(reviewId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteReviewHandleException() throws IOException { // deleteReview may throw IOException
        // Setup
        int reviewId = 99;
        // When deleteReview is called on the Mock Review DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).deleteReview(reviewId);

        // Invoke
        ResponseEntity<Review> response = reviewController.deleteReview(reviewId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}


