package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.controller.OrderController;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.OrderDAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the controller for the order class
 * 
 * @author Matt London
 */
@Tag("Controller-tier")
public class OrderControllerTest {
    private OrderController orderController;
    private OrderDAO orderDAO;

    /**
     * Build the controller before each test
     */
    @BeforeEach
    public void setupOrderController() {
        orderDAO = mock(OrderDAO.class);
        orderController = new OrderController(orderDAO);
    }

    /**
     * Reset the controller and DAO after each test
     */
    @AfterEach
    public void resetOrderController() {
        orderController = null;
        orderDAO = null;
    }

    /**
     * Make sure we can retrieve orders by ID
     * 
     * @throws IOException If the orderDAO cannot be accessed
     */
    @Test
    public void testGetOrder() throws IOException {
        // Assemble the order to be retrieved by the test
        Order order = new Order(1, 9, new Product[0], null);

        // Setup the mock object to return this order when retrieved
        when(orderDAO.getOrder(order.getId())).thenReturn(order);

        // Send the command to the controller
        ResponseEntity<Order> response = orderController.getOrder(order.getId());

        // Make sure the response matches
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    /**
     * Test getting a product by an ID that does not exist
     * 
     * @throws IOException If the orderDAO cannot access the file
     */
    @Test
    public void testGetOrderNotFound() throws IOException {
        // Return null when we receive the fakeId value
        int fakeId = 25;

        when(orderDAO.getOrder(fakeId)).thenReturn(null);

        // Call on the assigned ID
        ResponseEntity<Order> response = orderController.getOrder(fakeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Make sure we handle an exception when the DAO throws an IOException
     * 
     * @throws IOException When we cannot access the DAO
     */
    @Test
    public void testGetOrderHandleException() throws IOException {
        // Return null when we receive the fakeId value
        int fakeId = 25;

        doThrow(new IOException()).when(orderDAO).getOrder(fakeId);

        // Call the function
        ResponseEntity<Order> response = orderController.getOrder(fakeId);

        // Check that we receive a server error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
     * Try creating an order
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testCreateOrder() throws IOException {
        // Create an order and assign it's response
        Order order = new Order(1, 0, new Product[0], null);
        when(orderDAO.createOrder(order)).thenReturn(order);

        // Call the controller and check for an equivalent response
        ResponseEntity<Order> response = orderController.createOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(order, response.getBody());
    }

    /**
     * Try creating an order that fails to create
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testCreateOrderFailed() throws IOException {
        // Build a test order and assign it's response to null
        Order order = new Order(1, 0, new Product[0], null);

        when(orderDAO.createOrder(order)).thenReturn(null);

        // Call the controller and check for an equivalent response
        ResponseEntity<Order> response = orderController.createOrder(order);

        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    /**
     * Test the response for when get orders returns null as a value
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testGetOrdersNull() throws IOException {
        // Return null when called
        when(orderDAO.getOrders()).thenReturn(null);

        // Call the controller and check for an equivalent response
        ResponseEntity<Order[]> response = orderController.getOrders();

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    /**
     * Make sure we handle an exception when the DAO throws an IOException
     * 
     * @throws IOException When we cannot access the DAO
     */
    @Test
    public void testCreateOrderHandleException() throws IOException {
        // Set the response when attempting to create this order
        Order order = new Order(1, 0, new Product[0], null);

        doThrow(new IOException()).when(orderDAO).createOrder(order);

        // Call the function and check its response
        ResponseEntity<Order> response = orderController.createOrder(order);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /**
     * Test retrieving an array of all orders
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testGetOrders() throws IOException {
        // Create an array of orders to be returned by the DAO
        Order[] orders = new Order[2];
        orders[0] = new Order(1, 0, new Product[0], null);
        orders[1] = new Order(2, 0, new Product[0], null);
        
        // Return the array when we call getOrders()
        when(orderDAO.getOrders()).thenReturn(orders);

        // Call the controller and check that we recieve okay and the passed in array
        ResponseEntity<Order[]> response = orderController.getOrders();

        assertEquals(HttpStatus.OK,response.getStatusCode());

        assertEquals(orders, response.getBody());
    }

    /**
     * Test catching an exception when expecting an array of Orders
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testGetOrdersHandleException() throws IOException {
        // When we try and get all orders, instead throw an exception
        doThrow(new IOException()).when(orderDAO).getOrders();

        // Call the function and check its return
        ResponseEntity<Order[]> response = orderController.getOrders();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    /**
     * Make sure we can delete an order by its ID
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testDeleteOrder() throws IOException {
        // Create an order to be deleted
        int orderId = 1;
        
        // Set the response to successful
        when(orderDAO.deleteOrder(orderId)).thenReturn(true);

        ResponseEntity<Order> response = orderController.deleteOrder(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Make sure we can delete an order by its ID
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testDeleteOrderNotFound() throws IOException {
        // Create an order to be deleted
        int fakeId = 1;
        
        // Show that it failed to delete when passed
        when(orderDAO.deleteOrder(fakeId)).thenReturn(false);

        // Check that the response matches not found
        ResponseEntity<Order> response = orderController.deleteOrder(fakeId);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    /**
     * Test deleting an order and the DAO throwing an exception
     * 
     * @throws IOException If the DAO cannot be accessed
     */
    @Test
    public void testDeleteOrderHandleException() throws IOException {
        // Set the id to delete
        int fakeId = 2;
        
        // Throw when we try to delete
        doThrow(new IOException()).when(orderDAO).deleteOrder(fakeId);

        // Call the delete function and check for the server error
        ResponseEntity<Order> response = orderController.deleteOrder(fakeId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
