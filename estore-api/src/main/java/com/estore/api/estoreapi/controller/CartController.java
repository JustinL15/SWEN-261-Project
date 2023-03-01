package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.persistence.CartDAO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controls the REST requests and responses for a Cart
 * 
 * @author Matt London
 */
@RestController
@RequestMapping("carts")
public class CartController {
    private static final Logger LOG = Logger.getLogger(CartController.class.getName());
    private CartDAO cartDAO;

    /**
     * Construct a REST API controller for a {@link Cart}
     * 
     * @param cartDAO Data access object (Ex. FileDAO)
     */
    public CartController(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Cart cart} for the given id
     * 
     * @param id The id used to locate the {@link Cart cart}
     * 
     * @return ResponseEntity with {@link Cart cart} object and the HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable int id) {
        LOG.info("GET /carts/" + id);
        try {
            Cart cart = cartDAO.getCart(id);
            if (cart != null)
                return new ResponseEntity<Cart>(cart, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Cart cart} for a customer
     * 
     * @param cart - the {@link Cart cart} to create
     * @return ResponseEntity with created {@link Cart cart} object and HTTP
     *         status of CREATED
     *         ResponseEntity with HTTP status of CONFLICT if {@link Cart
     *         cart} object already exists
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        LOG.info("POST /carts " + cart);
        try {
            Cart result = cartDAO.createCart(cart);

            if (result != null) {
                return new ResponseEntity<Cart>(result, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Cart cart} with the provided {@linkplain Cart cart} object, if it exists
     *
     * @param cart The {@link Cart cart} to update
     *
     * @return ResponseEntity with updated {@link Cart cart} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
        LOG.info("PUT /carts " + cart);
        try {
            if(cartDAO.updateCart(cart) != null)
                return new ResponseEntity<Cart>(cart, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Cart cart} with the given id
     * 
     * @param id - the id of the {@link Cart cart} to delete
     * @return ResponseEntity HTTP status of OK if deleted
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Cart> deleteCart(@PathVariable int id) {
        LOG.info("Delete /carts/" + id);
        try {
            Cart cart = cartDAO.getCart(id);
            if (cart != null) {
                cartDAO.deleteCart(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
