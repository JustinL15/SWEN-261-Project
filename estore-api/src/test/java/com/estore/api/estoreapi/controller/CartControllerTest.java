package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ProductReference;
import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.persistence.ProductDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("Controller-tier")
public class CartControllerTest {
    private CartController cartController;
    private CartDAO mockCartDAO;

    @BeforeEach
    public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        cartController = new CartController(mockCartDAO);
    }

    @Test
    public void testGetCart() throws IOException {
        // setup
        Cart cart = new Cart(99);
        when(mockCartDAO.getCart(cart.getId())).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.getCart(cart.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testGetCartNotFound() throws Exception {
        int cartId = 99;
        when(mockCartDAO.getCart(cartId)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.getCart(cartId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCartHandleException() throws Exception { // getCart may throw IOException
        // Setup
        int cartId = 99;
        // When getCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).getCart(cartId);

        // Invoke
        ResponseEntity<Cart> response = cartController.getCart(cartId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateCart() throws IOException {
        Cart cart = new Cart(99);

        when(mockCartDAO.createCart(cart)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.createCart(cart);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testCreateCartFailed() throws IOException { // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // when createCart is called, return false simulating failed
        // creation and save
        when(mockCartDAO.createCart(null)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateCartHandleException() throws IOException { // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);

        // When createCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).createCart(cart);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testAddItemToCart() throws IOException{
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        
        ProductReference productRef = new ProductReference(80, 1);

        when(mockCartDAO.addItem(cart.getId(), 80, 1)).thenReturn(true);
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), productRef);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddItemToCartFailed() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");

        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        // when addItemToCart is called, return false simulating failed
        // creation and save
        ProductReference productRef = new ProductReference(80, 1);
        when(mockCartDAO.addItem(cart.getId(), 2, 1)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), productRef);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddItemToCartHandleException() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");

        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        // When addItemToCart is called on the Mock Cart DAO, throw an IOException
        ProductReference productRef = new ProductReference(80, 1);
        doThrow(new IOException()).when(mockCartDAO).addItem(cart.getId(), 80, 1);
        // Invoke
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), productRef);
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveItemFromCart() throws IOException {
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductReference productRef = new ProductReference(product.getId(), 2);

        // Remove item that exists
        when(mockCartDAO.removeItem(cart.getId(), productRef.getId())).thenReturn(true);


        ResponseEntity<Cart> responseSuccess = cartController.removeItemFromCart(cart.getId(), productRef.getId());

        assertEquals(HttpStatus.OK, responseSuccess.getStatusCode());
    }

    @Test
    public void testRemoveItemFromCartFailed() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(1, "logger", 10, 20, "log spoon");
        // creation and save
        when(mockCartDAO.removeItem(cart.getId(), product.getId())).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.removeItemFromCart(cart.getId(), product.getId());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveItemFromCartHandleException() throws IOException {
        // Setup
        Cart cart = new Cart(99);

        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        doThrow(new IOException()).when(mockCartDAO).removeItem(cart.getId(), product.getId());

        // Invoke
        ResponseEntity<Cart> response = cartController.removeItemFromCart(cart.getId(), product.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testEditItemInCart() throws IOException {
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        
        ProductReference productRef = new ProductReference(0, 2);
        // Also cover the set ID case for product ref
        productRef.setId(product.getId());

        when(mockCartDAO.editQuantity(cart.getId(), productRef.getId(), productRef.getQuantity())).thenReturn(true);

        ResponseEntity<Cart> response = cartController.editItemInCart(cart.getId(), productRef);
        response = cartController.editItemInCart(cart.getId(), productRef); // Add another 2 to the cart

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testEditItemInCartNotFound() throws IOException {
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        
        ProductReference productRef = new ProductReference(product.getId(), 2);

        when(mockCartDAO.editQuantity(cart.getId(), productRef.getId(), productRef.getQuantity())).thenReturn(false);

        ResponseEntity<Cart> response = cartController.editItemInCart(cart.getId(), productRef);
        response = cartController.editItemInCart(cart.getId(), productRef); // Add another 2 to the cart

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testEditItemInCartFailed() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(1, "logger", 10, 20, "log spoon");
        // when editItemInCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.editQuantity(cart.getId(), product.getId(), 1)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(cart);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testEditItemInCartHandleException() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        
        ProductReference productRef = new ProductReference(80, 2);

        // When editItemInCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).editQuantity(cart.getId(), productRef.getId(), productRef.getQuantity());

        // Invoke
        ResponseEntity<Cart> response = cartController.editItemInCart(cart.getId(), productRef);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateCart() throws IOException { // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // when createCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.updateCart(cart)).thenReturn(cart);
        ResponseEntity<Cart> response = cartController.updateCart(cart);

        // Invoke
        response = cartController.updateCart(cart);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testUpdateCartFailed() throws IOException { // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // when createCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.updateCart(cart)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(cart);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCartHandleException() throws IOException { // createCart may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // When createCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).updateCart(cart);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(cart);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteCart() throws IOException {
        Cart cart = new Cart(99);

        when(mockCartDAO.getCart(cart.getId())).thenReturn(cart);

        when(mockCartDAO.deleteCart(cart.getId())).thenReturn(true);

        ResponseEntity<Cart> response = cartController.deleteCart(cart.getId());
        response = cartController.getCart(cart.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testDeleteCartFailed() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        // when deleteCart is called, return true simulating successful
        // update and save
        when(mockCartDAO.deleteCart(0)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(cart.getId());
        response = cartController.getCart(cart.getId());
        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteCartHandleExpception() throws IOException {
        // Setup
        int id = 99;
        // When deleteCart is called on the Mock Cart DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).deleteCart(id);
        doThrow(new IOException()).when(mockCartDAO).getCart(id);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(id);
        
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
