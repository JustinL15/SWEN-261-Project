package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Tests for the file dao of order
 * 
 * @author Matt London
 */
@Tag("Persistence-tier")
public class OrderFileDAOTest {
    OrderFileDAO orderFileDAO;
    Order[] exampleOrders;
    ObjectMapper mockObjectMapper;

    /**
     * Assemble the mapper before each testl
     */
    @BeforeEach
    public void setupOrderFileDAO() throws IOException {
        // Build the mock mapper with some example orders
        mockObjectMapper = mock(ObjectMapper.class);
        exampleOrders = new Order[2];

        // Build a products list for the full example order
        Product[] exampleProducts0 = new Product[2];
        exampleProducts0[0] = new Product(1, "Coffee filter", 4.99, 10, "Filters for coffee");
        exampleProducts0[1] = new Product(2, "Coffee mug", 9.99, 10, "Mug for coffee");
        
        exampleOrders[0] = new Order(1, 10.99, exampleProducts0, false, null);
        exampleOrders[1] = new Order(2, 0, new Product[0], false, null);

        // Link the mapper to these values
        when(mockObjectMapper.readValue(new File("foo.txt"), Order[].class))
                .thenReturn(exampleOrders);
        orderFileDAO = new OrderFileDAO("foo.txt", mockObjectMapper);
    }

    /**
     * Set everything to null so that the garbage collector can clean it up
     * and we can reassign in the setup function
     */
    @AfterEach
    public void destructSetup() {
        mockObjectMapper = null;
        exampleOrders = null;
        orderFileDAO = null;
    }

    /**
     * Make sure that all orders are returned properly
     */
    @Test
    public void testGetAllOrders() {
        Order[] orders = orderFileDAO.getOrders();

        // Check if they are equivalent
        assertEquals(orders.length, exampleOrders.length);

        for (int ii = 0; ii < exampleOrders.length; ii++)
            assertEquals(orders[ii], exampleOrders[ii]);
    }

    /**
     * Test getting a specific order
     */
    @Test
    public void testGetOrder() {
        Order retrieved0 = orderFileDAO.getOrder(exampleOrders[0].getId());
        Order retrieved1 = orderFileDAO.getOrder(exampleOrders[1].getId());
        Order retrieved2 = orderFileDAO.getOrder(100); // Should not exist

        // Make sure the correct orders are returned
        assertEquals(retrieved0, exampleOrders[0]);
        assertEquals(retrieved1, exampleOrders[1]);
        assertNull(retrieved2);
    }

    /**
     * Try deleting an order that does exist and then one that does not exist
     */
    @Test
    public void testDeleteOrder() {
        boolean resultExists = assertDoesNotThrow(() -> orderFileDAO.deleteOrder(exampleOrders[0].getId()),
                            "Exception thrown on existing delete");

        assertTrue(resultExists);

        boolean resultNotExist = assertDoesNotThrow(() -> orderFileDAO.deleteOrder(100),
                            "Exception thrown on non-existing delete");

        assertFalse(resultNotExist);
        
        // Make sure deleting carries over to the size of the array
        assertEquals(orderFileDAO.orderMap.size(), exampleOrders.length - 1);
    }

    /**
     * Create an order and make sure it can be retrieved within the DAO
     */
    @Test
    public void testCreateOrder() {
        // Create the order
        Product[] newProducts = new Product[1];
        newProducts[0] = new Product(3, "Coffee maker", 10, 1, "Makes coffee");
        Order order = new Order(4, 10, newProducts, true, null);

        // Add it to the DAO
        Order result = assertDoesNotThrow(() -> orderFileDAO.createOrder(order),
                                "Unexpected exception thrown");

        // Make sure everything is equivalent
        assertNotNull(result);
        Order pulledOrder = orderFileDAO.getOrder(result.getId());
        assertEquals(pulledOrder.getId(), result.getId());
        assertEquals(pulledOrder.isComplete(), result.isComplete());
        assertEquals(pulledOrder, result);
    }

   /**
    * Make sure an exception is thrown when the mapper fails to write

    * @throws IOException When mapper cannot talk to the file
    */ 
    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Order[].class));

        Order order = new Order(3, 10, new Product[0], false, null);


        assertThrows(IOException.class,
                        () -> orderFileDAO.createOrder(order),
                        "IOException not thrown when mapper failed to write");
    }

    /**
     * Make sure a false value is returned when attepting to delete a non-existent order
     * Also ensure no changes are made to the array
     */
    @Test
    public void testDeleteNotFoundOrder() {
        // Try to delete the order
        boolean result = assertDoesNotThrow(() -> orderFileDAO.deleteOrder(100),
                                                "Exception thrown when deleting non-existant order");

        // Make sure the result is false and the array is the same size
        assertEquals(result, false);
        assertEquals(orderFileDAO.orderMap.size(), exampleOrders.length);

    }

    /**
     * Check what happens if we throw an IOException when attempting to read the file
     * 
     * @throws IOException When the mapper cannot read the file
     */
    @Test
    public void testConstructorException() throws IOException {
        // Rebuild the mapper so that it will throw exceptions
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("foo.txt"), Order[].class);

        // Attempt to construct and make sure that an exception is thrown
        assertThrows(IOException.class,
                        () -> new OrderFileDAO("foo.txt", mockObjectMapper),
                        "IOException not thrown");
    }

    /**
     * Check what happens if we throw an IOException when attempting to read the file
     * 
     * @throws IOException When the mapper cannot read the file
     */
     @Test
    public void testUpdateOrder() throws IOException {
       // Setup
       Order order = new Order(3, 10, new Product[0], false, null);
       orderFileDAO.createOrder(order);

       // Invoke
        Order result = assertDoesNotThrow(() -> orderFileDAO.updateOrder(order), "Unexpected exception thrown");

       // Analyze
       assertNotNull(result);
       Order actual = orderFileDAO.getOrder(order.getId());
       assertEquals(actual,order);
    }
}
