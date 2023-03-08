package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Order model
 * 
 * @author Matt London
 */
@Tag("Model-tier")
public class OrderTest {
    /**
     * Test that the constructor can successfully create an order
     */
    @Test
    public void testConstructor() {
        // Setup the test parameters
        int id = 1;
        Product[] products = new Product[1];
        products[0] = new Product(2, "test", 4.99, 1, "");
        double totalPrice = products[0].getPrice();
        LocalDateTime datetime = LocalDateTime.now();
        boolean complete = false;

        // Call constructor
        Order order = new Order(id, totalPrice, products, complete, datetime);

        // Check equivalence
        assertEquals(id, order.getId());
        assertEquals(totalPrice, order.getTotalPrice());
        assertEquals(products, order.getProducts());
        assertEquals(datetime, order.getDateTime());
        assertEquals(complete, order.isComplete());
    }

    /**
     * Make sure all setters will then reflect the set value
     */
    @Test
    public void testSetters() {
        // Build a blank object
        Order order = new Order(0, 0, new Product[0], false, null);

        // Test the setters
        int id = 2;
        order.setId(id);
        assertEquals(id, order.getId());

        Product[] products = new Product[1];
        products[0] = new Product(2, "test", 4.99, 1, "");

        order.setProducts(products);
        assertEquals(products, order.getProducts());

        double totalPrice = products[0].getPrice();
        order.setTotalPrice(totalPrice);
        assertEquals(totalPrice, order.getTotalPrice());

        boolean complete = true;
        order.setComplete(complete);
        assertEquals(complete, order.isComplete());

    }

    /**
     * Test the equals functionality against objects that are not equal
     */
    @Test
    public void testEqualsFalse() {
        Product[] subjectProducts = new Product[1];
        subjectProducts[0] = new Product(2, "test", 4.99, 1, "");
        Order subject = new Order(1, 4.99, subjectProducts, false, null);

        // Test against a different object
        String string = "test";
        assertFalse(subject.equals(string));

        // Test against a different product size
        Order noProducts = new Order(1, 0, new Product[0], true, null);
        assertFalse(subject.equals(noProducts));

        // Test against a different product
        Product[] differentProducts = new Product[1];
        differentProducts[0] = new Product(3, "test", 4.99, 1, "");

        Order differentProduct = new Order(1, 4.99, differentProducts, true, null);
        assertFalse(subject.equals(differentProduct));

    }
}