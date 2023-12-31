package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.estore.api.estoreapi.model.Order;

/**
 * The unit test suite for the Customer class
 * 
 * @author Alexandria Pross
 */

@Tag("Model-tier")
public class CustomerTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_username = "user1234";
        String expected_name = "John Smith";
        int expected_cartId = 77;
        boolean expected_isAdmin = false;
        String expected_password = "Sw3niZc00L";


        // Invoke
        Customer customer = new Customer(expected_id,expected_username,expected_name,expected_cartId,expected_isAdmin,expected_password);
        customer.setId(expected_id);
        customer.setCartId(expected_cartId);


        // Analyze
        assertEquals(expected_id,customer.getId());
        assertEquals(expected_username,customer.getUsername());
        assertEquals(expected_name,customer.getName());
        assertEquals(expected_cartId,customer.getCartId());
        assertEquals(expected_isAdmin,customer.isAdmin());
    }

    @Test
    public void testUsername() {
        // Setup
        int id = 99;
        String username = "user1234";
        String name = "John Smith";
        int cartId = 77;
        boolean isAdmin = false;
        String password = "Sw3niZcooL";
        Customer customer = new Customer(id,username,name,cartId,isAdmin,password);

        String expected_username = "user9876";

        // Invoke
        customer.setUsername(expected_username);

        // Analyze
        assertEquals(expected_username,customer.getUsername());
    }

    @Test
    public void testName() {
        // Setup
        int id = 99;
        String username = "user1234";
        String name = "John Smith";
        int cartId = 77;
        boolean isAdmin = false;
        String password = "Sw3niZcooL";
        Customer customer = new Customer(id,username,name,cartId,isAdmin,password);

        String expected_name = "Smith Johnson";

        // Invoke
        customer.setName(expected_name);

        // Analyze
        assertEquals(expected_name,customer.getName());
    }

    @Test
    public void testOrders() {
        // Setup
        int id = 99;
        String username = "user1234";
        String name = "John Smith";
        int cartId = 77;
        boolean isAdmin = false;
        String password = "Sw3niZcooL";
        Customer customer = new Customer(id,username,name,cartId,isAdmin,password);

        int order_id = 10;
        List<Integer> orders;
        // Invoke
        customer.addOrder(order_id);
        orders = customer.getOrders();

        // Analyze
        assertEquals(orders,customer.getOrders());
    }

    @Test
    public void testPassword() {
        // Setup
        int id = 99;
        String username = "user1234";
        String name = "John Smith";
        int cartId = 77;
        boolean isAdmin = false;
        String password = "Sw3niZcooL";
        String password2 = "Sw3niZcooL2";
        Customer customer = new Customer(id,username,name,cartId,isAdmin,password);

        // Analyze
        assertEquals(password,customer.getPassword());

        // Check setting password
        customer.setPassword(password2);
        assertEquals(password2,customer.getPassword());

    }

    @Test
    public void testProductId() {
        // Setup
        int id = 99;
        String username = "user1234";
        String name = "John Smith";
        int cartId = 77;
        boolean isAdmin = false;
        String password = "Sw3niZcooL";
        Customer customer = new Customer(id,username,name,cartId,isAdmin,password);

        int product_id = 10;
        List<Integer> products;
        // Invoke
        customer.addPurchasedProduct(product_id);
        products = customer.getPurchasedProducts();

        // Analyze
        assertEquals(products,customer.getPurchasedProducts());
    }

}

