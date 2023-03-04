package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.persistence.OrderDAO;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controls the REST requests and responses for an Order
 * 
 *@author Alexandria Pross
 */
 
@RestController
@RequestMapping("orders")
public class OrderController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private OrderDAO orderDAO;

    /**
     * Construct a REST API controller for a {@link Order}
     * 
     * @param orderDAO Data access object (Ex. FileDAO)
     */
    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Order order} for the given id
     * 
     * @param id The id used to locate the {@link Order order}
     * 
     * @return ResponseEntity with {@link Order order} object and the HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable int id) {
        LOG.info("GET /orders/" + id);
        try {
            Order order = orderDAO.getOrder(id);
            if (order != null)
                return new ResponseEntity<Order>(order, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for a {@linkplain Order order} for all ids in the array
     * 
     * @param id The id used to locate the {@link Order order}
     * 
     * @return ResponseEntity with {@link Order order} object and the HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
     @GetMapping("")
     public ResponseEntity<Order[]> getOrders() {
 
         LOG.info("GET /orders");
         try {
             Order[] orders = orderDAO.getOrders();
             if(orders != null)
                 return new ResponseEntity<Order[]>(orders, HttpStatus.OK);
             else
                 return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
         }
         catch(IOException e) {
             LOG.log(Level.SEVERE,e.getLocalizedMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
         }
         
     }

     /**
     * Creates a {@linkplain Order order} with product object
     * 
     * @param order - the {@link Order order} to create
     * @return ResponseEntity with created {@link Order order} object and HTTP
     *         status of CREATED
     *         ResponseEntity with HTTP status of CONFLICT if {@link Product
     *         product} object already exists
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        LOG.info("POST /order " + order);
        try {

            Order result = orderDAO.createOrder(order);

            if (result != null) {
                return new ResponseEntity<Order>(result, HttpStatus.CREATED);
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
     * Deletes a {@linkplain Order order} with the given id
     * 
     * @param id - the id of the {@link Order order} to delete
     * @return ResponseEntity HTTP status of OK if deleted
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable int id) {
        LOG.info("Delete /orders/" + id);
        try {
            Order order = orderDAO.getOrder(id);
            if (order != null) {
                orderDAO.deleteOrder(id);
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
