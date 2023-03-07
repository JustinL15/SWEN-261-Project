package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for Product class
 * 
 * @author Jessica Eisler
 */
@Tag("Model-tier")
public class ProductTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        double expected_price = 20;
        int expected_quantity = 5;
        String expected_desc = "cool name";

        // Invoke
        Product product = new Product(expected_id,expected_name, expected_price, 
        expected_quantity, expected_desc);

        // Analyze
        assertEquals(expected_id,product.getId());
        assertEquals(expected_name,product.getName());
    }

    @Test
    public void testCtor2() {
        // Setup
        int expected_id = 99;
        String expected_name = "Wi-Fire";
        double expected_price = 20;
        int expected_quantity = 5;
        String expected_desc = null;

        // Invoke
        Product product = new Product(expected_id,expected_name, expected_price, 
        expected_quantity, expected_desc);

        // Analyze
        assertEquals(expected_id,product.getId());
        assertEquals(expected_name,product.getName());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price = 20;
        int quantity = 5;
        String desc = "cool name";
        Product product = new Product(id,name,price,quantity,desc);

        int expected_Id = 100;

        // Invoke
        product.setId(expected_Id);

        // Analyze
        assertEquals(expected_Id,product.getId());
    }

    @Test
    public void testPrice(){
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price = 20;
        int quantity = 5;
        String desc = "cool name";
        Product product = new Product(id,name,price,quantity,desc);

        double expected_price = 2;

        // Invoke
        product.setPrice(expected_price);

        // Analyze
        assertEquals(expected_price,product.getPrice());
    }

    @Test
    public void testQuantity(){
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price = 20;
        int quantity = 5;
        String desc = "cool name";
        Product product = new Product(id,name,price,quantity,desc);

        int espected_quantity = 2;

        // Invoke
        product.setQuantity(espected_quantity);

        // Analyze
        assertEquals(espected_quantity,product.getQuantity());
    }

    @Test
    public void testDescription(){
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price = 20;
        int quantity = 5;
        String desc = "cool name";
        Product product = new Product(id,name,price,quantity,desc);

        String espected_desc = "wow how cool";

        // Invoke
        product.setDescription(espected_desc);

        // Analyze
        assertEquals(espected_desc,product.getDescription());
    }

    @Test
    public void testId(){
                // Setup
                int id = 99;
                String name = "Wi-Fire";
                double price = 20;
                int quantity = 5;
                String desc = "cool name";
                Product product = new Product(id,name,price,quantity,desc);
        
                String expected_name = "Galactic Agent";
        
                // Invoke
                product.setName(expected_name);
        
                // Analyze
                assertEquals(expected_name,product.getName());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String name = "Wi-Fire";
        double price = 20;
        int quantity = 5;
        String desc = "cool name";
        Product product = new Product(id,name,price,quantity,desc);
        String expected_string = product.toString();


        // Invoke
        String actual_string = product.toString();

        // Analyze
        assertEquals(expected_string,actual_string);
    }
}
