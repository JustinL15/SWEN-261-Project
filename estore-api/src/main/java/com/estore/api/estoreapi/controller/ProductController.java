package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controls the REST requests and responses for a Propduct
 * 
 * @author Matt London
 * @author ADD NAMES HERE
 */
@RestController
@RequestMapping("product")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDAO;


    /// TODO This may need more functions to complete the sprint, these are the template ones I have
    /// written so far

    /**
     * Construct a REST API controller for a {@link Product}
     * 
     * @param productDAO Data access object (Ex. FileDAO)
     */
    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(int id) {
        // TODO implement and write docstring
        return null;
    }

    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {
        // TODO implement and write docstring
        return null;
    }

    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String text) {
        // TODO implement and write docstring
        return null;
    }

    @GetMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // TODO implement and write docstring
        return null;
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        // TODO implement and write docstring
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(int id) {
        // TODO implement and write docstring
        return null;
    }
}
