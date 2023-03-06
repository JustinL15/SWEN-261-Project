package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.persistence.CartDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    public void setupCartController() {
        mockCartDAO = mock(CartDAO.class);
        cartController = new CartController(mockCartDAO);
    }

    @Test
    public void testGetCart() throws IOException{
        //setup
        Cart cart = new Cart(99);
        when(mockCartDAO.createCart(cart)).thenReturn(null);
        int cartId = cart.getId();
        Cart[] carts = new Cart[1];
        carts[0] = new Cart(cartId);
        when(mockCartDAO.getCart(cartId)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.getCart(cart.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testGetCartNotFound() throws Exception{
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
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }


    @Test
    public void testCreateCart() throws IOException{
        Cart cart = new Cart(98);
        
        when(mockCartDAO.createCart(cart)).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.getCart(cart.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cart, response.getBody());
    }

    @Test
    public void testCreateCartFailed() throws IOException {  // createHero may throw IOException
        // Setup
        Cart cart = new Cart(99);
        // when createHero is called, return false simulating failed
        // creation and save
        when(mockCartDAO.createCart(cart)).thenReturn(null);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateCartHandleException() throws IOException {  // createHero may throw IOException
        // Setup
        Cart cart = new Cart(99);

        // When createHero is called on the Mock Hero DAO, throw an IOException
        doThrow(new IOException()).when(mockCartDAO).createCart(cart);

        // Invoke
        ResponseEntity<Cart> response = cartController.createCart(cart);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testAddItemToCart() throws IOException{
        Cart cart = new Cart(99);

        // cart.addItem(2, 1);
        when(mockCartDAO.createCart(cart)).thenReturn(null);
        int searchId = cart.getId();
        Cart [] carts = new Cart[1];
        carts[0] = new Cart(99);
        when(mockCartDAO.addItem(cart.getId(), 2, 1)).thenReturn(null);
    }

    @Test
    public void testAddItemToCartFailed() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testAddItemToCartHandleException() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testRemoveItemToCart() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testRemoveItemToCartFailed() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testRemoveItemToCartHandleException() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testEditItemInCart() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testEditItemInCartFailed() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testEditItemInCartHandleException() throws IOException{
        Cart cart = new Cart(99);
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
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cart,response.getBody());
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
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
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
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteCart() throws IOException{
        Cart cart = new Cart(99);
    }

    @Test
    public void testDeleteCartFailed() throws IOException{
        Cart cart = new Cart(99);
    }
    @Test
    public void testDeleteCartHandleExpception() throws IOException{
        Cart cart = new Cart(99);
    }
}
