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
    public void testGetCartHandleException() throws Exception { // createHero may throw IOException
        // Setup
        int cartId = 99;
        // When getHero is called on the Mock Hero DAO, throw an IOException
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
    public void testCreateCartFailed() throws IOException { // createHero may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // when createHero is called, return false simulating failed
        // creation and save
        when(mockCartDAO.createCart(null)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateCartHandleException() throws IOException { // createHero may throw IOException
        // Setup
        Cart cart = new Cart(99);

        // When createHero is called on the Mock Hero DAO, throw an IOException
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
        
        ProductReference productRef = new ProductReference(80, 2);

        //when(mockCartDAO.createCart(cart)).thenReturn(null);
        when(mockCartDAO.addItemToCart(cart.getId(), productRef)).thenReturn(cart);
        when(mockCartDAO.addItem(cart.getId(), 2, 1)).thenReturn(true);
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), productRef);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testAddItemToCartFailed() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        // when createHero is called, return false simulating failed
        // creation and save
        when(mockCartDAO.addItem(cart.getId(), 2, 1)).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), null);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddItemToCartHandleException() throws IOException {
        // Setup
        Cart cart = new Cart(99);

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).addItem(cart.getId(), 2, 1);

        // Invoke
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testRemoveItemFromCart() throws IOException {
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        
        ProductReference productRef = new ProductReference(80, 2);

        cart.addItem(product.getId(), 2);

        when(mockCartDAO.removeItem(cart.getId(), 2)).thenReturn(true);

        ResponseEntity<Cart> response = cartController.removeItemFromCart(cart.getId(), productRef.getId());
        response = cartController.getCart(cart.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testRemoveItemFromCartFailed() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(1, "logger", 10, 20, "log spoon");
        // when createHero is called, return false simulating failed
        // creation and save
        when(mockCartDAO.removeItem(cart.getId(), product.getId())).thenReturn(false);

        // Invoke
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), null);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRemoveItemFromCartHandleException() throws IOException {
        // Setup
        Cart cart = new Cart(99);

        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        
        ProductReference productRef = new ProductReference(80, 2);

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).removeItem(cart.getId(), 0)

        // Invoke
        ResponseEntity<Cart> response = cartController.addItemToCart(cart.getId(), null);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testEditItemInCart() throws IOException {
        Cart cart = new Cart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky");
        
        ProductDAO mockProductDAO = mock(ProductDAO.class);
        mockProductDAO.createProduct(product);
        
        ProductReference productRef = new ProductReference(80, 2);

        when(mockCartDAO.editQuantity(cart.getId(), product.getId(), product.getQuantity() - 1)).thenReturn(true);

        ResponseEntity<Cart> response = cartController.removeItemFromCart(cart.getId(), productRef.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testEditItemInCartFailed() throws IOException {
        // Setup
        Cart cart = new Cart(99);
        Product product = new Product(1, "logger", 10, 20, "log spoon");
        // when updateHero is called, return true simulating successful
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

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).addItem(cart.getId(), 2, 1);
        doThrow(new IOException()).when(mockCartDAO).getCart(cart.getId());

        // Invoke
        ResponseEntity<Cart> response = cartController.editItemInCart(cart.getId(), productRef);
        response = cartController.getCart(cart.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateCart() throws IOException { // updateHero may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // when updateHero is called, return true simulating successful
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
    public void testUpdateCartFailed() throws IOException { // updateHero may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // when updateHero is called, return true simulating successful
        // update and save
        when(mockCartDAO.updateCart(cart)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.updateCart(cart);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateCartHandleException() throws IOException { // updateHero may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // When updateHero is called on the Mock Hero DAO, throw an IOException
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
        // when updateHero is called, return true simulating successful
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
        // When updateHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).deleteCart(id);
        doThrow(new IOException()).when(mockCartDAO).getCart(id);

        // Invoke
        ResponseEntity<Cart> response = cartController.deleteCart(id);
        
        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
