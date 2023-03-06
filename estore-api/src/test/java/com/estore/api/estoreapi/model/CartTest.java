package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Cart class
 * 
 * @author Alexandria Pross
 */
@Tag("Model-tier")
public class CartTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;

        // Invoke
        Cart cart = new Cart(expected_id);

        // Analyze
        assertEquals(expected_id,cart.getId());
    }

    @Test
    public void testInventory() {
        // Setup
        int expected_id = 99;

        // Invoke
        Cart cart = new Cart(expected_id);

        int product_id = 10;
        int quantity = 2;
        cart.addItem(product_id, quantity);
        HashMap<Integer, ProductReference> inventory;
        inventory = cart.getInventory();

        // Analyze
        assertEquals(inventory,cart.getInventory());
    }

    @Test
    public void testInventoryEdits() {
        // Setup
        int expected_id = 99;

        // Invoke
        Cart cart = new Cart(expected_id);

        int product_id = 10;
        int quantity = 2;
        int product_id_2 = 7;
        int quantity_2 = 1;
        cart.addItem(product_id, quantity);
        cart.addItem(product_id_2, quantity_2);
        cart.removeItem(product_id_2);
        cart.editQuantity(product_id, quantity_2);
        HashMap<Integer, ProductReference> inventory;
        inventory = cart.getInventory();

        // Analyze
        assertEquals(inventory,cart.getInventory());
    }

    @Test
    public void testInventoryClear() {
        // Setup
        int expected_id = 99;

        // Invoke
        Cart cart = new Cart(expected_id);

        int product_id = 10;
        int quantity = 2;
        cart.addItem(product_id, quantity);
        cart.clear();
        HashMap<Integer, ProductReference> inventory;
        inventory = cart.getInventory();

        // Analyze
        assertEquals(inventory,cart.getInventory());
    }

}
