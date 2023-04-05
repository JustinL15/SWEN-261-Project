package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.ReviewDAO;
import com.estore.api.estoreapi.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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
        Review review = new Review(99,88,77,2, "good product", "asdadas");
        // When the same id is passed in, our mock Review DAO will return the Customer object
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
        // When getReview is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockReviewDAO).getReview(reviewId);

        // Invoke
        ResponseEntity<Customer> response = customerController.getCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all CustomerController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateCustomer() throws IOException {  // createCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Wi-Fire","Wi-Fire", 153, false, "astyhhas");
        // when createCustomer is called, return true simulating successful
        // creation and save
        when(mockCustomerDAO.createCustomer(customer)).thenReturn(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(customer,response.getBody());
    }
    
    @Test
    public void testCreateCustomerFailed() throws IOException {  // createCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Bolt","Bolt", 153, false, "astyhhas");
        // when createCustomer is called, return false simulating failed
        // creation and save
        when(mockCustomerDAO.createCustomer(customer)).thenReturn(null);
        String searchString = customer.getName();
        Customer[] customers = new Customer[1];
        customers[0] = new Customer(99,"Bolt","Bolt", 153, false, "astyhhas");
        // When findCustomers is called with the search string, return the two
        /// customers above
        when(mockCustomerDAO.findCustomers(searchString)).thenReturn(customers);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateCustomerHandleException() throws IOException {  // createCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Ice Gladiator", "Ice Gladiator", 384, true, "ayths");

        // When createCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).createCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.createCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateCustomer() throws IOException { // updateCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Wi-Fire","Wi-Fire", 153, false, "astyhhas");
        // when updateCustomer is called, return true simulating successful
        // update and save
        when(mockCustomerDAO.updateCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);
        customer.setName("Bolt");

        // Invoke
        response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customer,response.getBody());
    }

    @Test
    public void testUpdateCustomerFailed() throws IOException { // updateCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Galactic Agent", "Galactic Agent", 123, false, "asdadas");
        // when updateCustomer is called, return true simulating successful
        // update and save
        when(mockCustomerDAO.updateCustomer(customer)).thenReturn(null);
        

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateCustomerHandleException() throws IOException { // updateCustomer may throw IOException
        // Setup
        Customer customer = new Customer(99,"Galactic Agent", "Galactic Agent", 123, false, "asdadas");
        // When updateCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).updateCustomer(customer);

        // Invoke
        ResponseEntity<Customer> response = customerController.updateCustomer(customer);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetCustomers() throws IOException { // getCustomers may throw IOException
        // Setup
        Customer[] customers = new Customer[2];
        customers[0] = new Customer(99,"Bolt","Bolt", 153, false, "astyhhas");
        customers[1] = new Customer(100,"The Great Iguana","The Great Iguana", 153, true, "jkyhhas");
        // When getCustomers is called return the customers created above
        when(mockCustomerDAO.getCustomers()).thenReturn(customers);

        // Invoke
        ResponseEntity<Customer[]> response = customerController.getCustomers();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(customers,response.getBody());
    }

    @Test
    public void testGetCustomersNotFound() throws IOException { // getCustomers may throw IOException
        when(mockCustomerDAO.getCustomers()).thenReturn(null);

        // Invoke
        ResponseEntity<Customer[]> response = customerController.getCustomers();

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetCustomersHandleException() throws IOException { // getCustomers may throw IOException
        // Setup
        // When getCustomers is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).getCustomers();

        // Invoke
        ResponseEntity<Customer[]> response = customerController.getCustomers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomer() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // when deleteCustomer is called return true, simulating successful deletion
        when(mockCustomerDAO.deleteCustomer(customerId)).thenReturn(true);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerNotFound() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // when deleteCustomer is called return false, simulating failed deletion
        when(mockCustomerDAO.deleteCustomer(customerId)).thenReturn(false);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerHandleException() throws IOException { // deleteCustomer may throw IOException
        // Setup
        int customerId = 99;
        // When deleteCustomer is called on the Mock Customer DAO, throw an IOException
        doThrow(new IOException()).when(mockCustomerDAO).deleteCustomer(customerId);

        // Invoke
        ResponseEntity<Customer> response = customerController.deleteCustomer(customerId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

}


