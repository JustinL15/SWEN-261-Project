package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductDAO;

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
 * Controls the REST requests and responses for a Propduct
 * 
 * @author Matt London
 * @author Alexandria Pross
 * @author Alexis Sanders
 * @author Jessica Eisler
 * @author Justin Lin
 */
@RestController
@RequestMapping("/products")
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

    /**
     * Responds to the GET request for a {@linkplain Product product} for the given id
     * 
     * @param id The id used to locate the {@link Product product}
     * 
     * @return ResponseEntity with {@link Product product} object and the HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(int id) {
        LOG.info("GET /products/" + id);
        try {
            Product product = productDAO.getProduct(id);
            if (product != null)
                return new ResponseEntity<Product>(product, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responsible for get all products located in the inventory
     * 
     * @return Response entity with a list of all the products
     * Status of OK
     * Status of INTERNAL_SERVICE_ERROR otherwise
     */

    @GetMapping("")
    public ResponseEntity<Product[]> getProducts() {

        LOG.info("GET /products");
        try {
            Product[] product = productDAO.getProducts(); //creates variable for products
            if(product != null) //if product is not null
                return new ResponseEntity<Product[]>(HttpStatus.OK); //set status to ok
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); //set status to not found
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); //set status to internal service error
        }
        
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name contains
     * the text in name
     *
     * @param text The text parameter which contains the text used to find the {@link Product products}
     *
     * @return ResponseEntity with array of {@link Product product} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * <p>
     * Example: Find all products that contain the text "ma"
     * GET http://localhost:8080/products/?text=ma
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String text) {
        LOG.info("GET /products/?text="+text);
        try {
            Product[] products = productDAO.findProducts(text);
            return new ResponseEntity<Product[]>(products,HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    
    /**
     * Creates a {@linkplain Product product} with product object
     * 
     * @param product - the {@link Product product} to create
     * @return ResponseEntity with created {@link Product product} object and HTTP
     *         status of CREATED
     *         ResponseEntity with HTTP status of CONFLICT if {@link Product
     *         product} object already exists
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        LOG.info("POST /products " + product);
        try {
            Product[] products = productDAO.findProducts(product.getName());
            for (Product currenProduct : products) {
                if (product.getName().equals(currenProduct.getName())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            productDAO.createProduct(product);
            return new ResponseEntity<Product>(product, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Product product} with the provided {@linkplain Product product} object, if it exists
     *
     * @param product The {@link Product product} to update
     *
     * @return ResponseEntity with updated {@link Product product} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        LOG.info("PUT /products " + product);
        try {
            if(productDAO.updateProduct(product) != null)
                return new ResponseEntity<Product>(product,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    /**
     * Deletes a {@linkplain Product product} with the given id
     * 
     * @param id - the id of the {@link Product product} to delete
     * @return ResponseEntity HTTP status of OK if deleted
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(int id) {
        LOG.info("Delete /products/" + id);
        try {
            Product product = productDAO.getProduct(id);
            if (product != null) {
                productDAO.deleteProduct(id);
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
