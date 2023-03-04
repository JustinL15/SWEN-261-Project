package com.estore.api.estoreapi.controller;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.persistence.CustomerDAO;

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
 * Controls the REST requests and responses for a customer object
 * 
 * @author Matt London

 */
@RestController
@RequestMapping("customers")
public class CustomerController {
    private static final Logger LOG = Logger.getLogger(CustomerController.class.getName());
    private CustomerDAO customerDAO;

    /**
     * Construct a REST API controller for a {@link Customer}
     * 
     * @param customerDAO Data access object for the customer
     */
    public CustomerController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Customer customer} for its id
     * 
     * @param id The id for {@link Customer customer}
     * 
     * @return ResponseEntity with {@link Customer customer} object and HTTP status - OK if found, NOT_FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
        LOG.info("GET /customers/" + id);
        try {
            Customer customer = customerDAO.getCustomer(id);
            if (customer != null) {
                return new ResponseEntity<Customer>(customer, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responsible for get all {@link Customer customers} in the DAO
     * 
     * @return Response entity with list of all customers and HTTP status - OK if found, NOT_FOUND if not found
     */

    @GetMapping("")
    public ResponseEntity<Customer[]> getCustomers() {

        LOG.info("GET /customers");
        try {
            Customer[] customers = customerDAO.getCustomers();
            // Return created customer
            if(customers != null) {
                return new ResponseEntity<Customer[]>(customers, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    /**
     * Responds to the GET request to search {@linkplain Customer customers} for matching names
     * 
     * @param name Name to search for
     * 
     * @return ResponseEntity with array of {@link Customer customers} and HTTP status - OK if found, NOT_FOUND if not found
     */
    @GetMapping("/")
    public ResponseEntity<Customer[]> searchCustomer(@RequestParam String name) {
        LOG.info("GET /customers/?name=" + name);
        try {
            Customer[] customers = customerDAO.findCustomers(name);
            return new ResponseEntity<Customer[]>(customers, HttpStatus.OK);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Customer customer}
     * 
     * @param customer - the {@link Customer customer} serialized object to create
     * @return {@link Customer customer} object and HTTP status of CREATED if created, CONFLICT otherwise
     */
    @PostMapping("")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        LOG.info("POST /customers " + customer);
        try {
            Customer[] matchingCustomers = customerDAO.findCustomers(customer.getUsername());
            for (Customer currentCustomer : matchingCustomers) {
                if (currentCustomer.getUsername().equals(customer.getUsername())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }

            Customer result = customerDAO.createCustomer(customer);

            if (result != null) {
                return new ResponseEntity<Customer>(result, HttpStatus.CREATED);
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
     * Updates the {@linkplain Customer customer} against the provided @{@link Customer}
     *
     * @param customer The {@link Customer customer} to update
     *
     * @return ResponseEntity with updated {@link Customer customer} and HTTP status of OK if updated
     * NOT_FOUND if not found
     */
    @PutMapping("")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        LOG.info("PUT /customers " + customer);
        try {
            if(customerDAO.updateCustomer(customer) != null)
                return new ResponseEntity<Customer>(customer, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Customer customer} with the passed ID
     * 
     * @param id - the id of the {@link Customer} to delete
     * @return ResponseEntity HTTP status of OK if deleted, NOT_FOUND if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable int id) {
        LOG.info("Delete /customers/" + id);
        try {
            Customer customer = customerDAO.getCustomer(id);
            if (customer != null) {
                customerDAO.deleteCustomer(id);

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
