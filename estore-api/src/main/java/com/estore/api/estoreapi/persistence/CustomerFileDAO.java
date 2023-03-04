package com.estore.api.estoreapi.persistence;

import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.util.TreeMap;

import com.estore.api.estoreapi.model.Customer;

/**
 * Implementation of CustomerDAO which saves all data to a file
 * 
 * @author Matt London
 */
@Component
public class CustomerFileDAO  implements CustomerDAO {
    private static final Logger LOG = Logger.getLogger(CustomerFileDAO.class.getName());
    
    /** Maps ids to their corresponding {@link Customer} */
    Map<Integer, Customer> customerMap;

    /** JSON serializer/deserializer */
    private ObjectMapper objectMapper;

    /** Next id to assign */
    private static int nextId;

    /** Filename of the json to store the info in */
    private String filename;

    /**
     * Build the {@link CustomerFileDAO} with the given filename
     * 
     * @param filename Filename to store the data in
     * @param objectMapper JSON serializer/deserializer
     * 
     * @throws IOException If there is an error reading the file
     */
    public CustomerFileDAO(@Value("${customers.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Gets next id and increments it
     * 
     * Syncronized to prevent multiple threads from accessing nextId concurrently
     * 
     * @return The next id to assign
     */
    private synchronized static int getNextId() {
        return nextId++;
    }

    /**
     * Gets array of {@link Customer customers} from the file
     * 
     * @return Array of stored {@link Customer customers}, or empty array if none
     */
    private Customer[] getCustomersArray() {
        return searchCustomers(null);
    }

    /**
     * Searches through the array of {@link Customer} for those matching some text
     * 
     * @param name Name to search for
     * @return Matching {@link Customer customers}, or empty array if none
     */
    private Customer[] searchCustomers(String name) {
        List<Customer> matchingCustomers = new ArrayList<>();

        for (Customer customer : customerMap.values()) {
            // Look for matching name or username
            if ((name == null || (customer.getName() != null
                    && customer.getName().toLowerCase().contains(name.toLowerCase())))
                    || (name != null && customer.getUsername() != null
                    && customer.getUsername().toLowerCase().contains(name.toLowerCase()))) {
                        matchingCustomers.add(customer);
            }
        }

        Customer[] customerArray = new Customer[matchingCustomers.size()];
        matchingCustomers.toArray(customerArray);


        return customerArray;
    }

    /**
     * Saves {@link Customer} to the DAO file
     * 
     * @throws IOException If there is an error writing to the file
     */
    private void save() throws IOException{
        Customer[] allCustomers = getCustomersArray();

        objectMapper.writeValue(new File(filename), allCustomers);
    }

    /**
     * Load all {@link Customer}s from file and set into map
     * 
     * @throws IOException If there is an error reading the file
     */
    private void load() throws IOException {
        customerMap = new TreeMap<>();
        nextId = 0;

        Customer[] customersSerialized = objectMapper.readValue(new File(filename), Customer[].class);

        // Save all customers into map and find next id
        for (Customer customer: customersSerialized) {
            customerMap.put(customer.getId(), customer);
            if (customer.getId() > nextId) {
                nextId = customer.getId();
            }
        }

        // Increment ID
        getNextId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer[] getCustomers() {
        // Sync on customerMap so that only one function can access it at a time
        synchronized(customerMap) {
            return getCustomersArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer[] findCustomers(String name) {
        synchronized(customerMap) {
            return searchCustomers(name);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getCustomer(int id) {
        synchronized(customerMap) {
            return customerMap.get(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer createCustomer(Customer customer) throws IOException {
        synchronized(customerMap) {
            Customer customerTmp = new Customer(getNextId(), customer.getUsername(), customer.getName(),
                                                customer.getCartId(), customer.isAdmin(), customer.getPassword());
            
            // Add to map and save to DAO
            customerMap.put(customerTmp.getId(), customerTmp);
            save();

            return customerTmp;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer updateCustomer(Customer customer) throws IOException {
        synchronized(customerMap) {
            // If customer doesn't exist, return null
            if (!customerMap.containsKey(customer.getId())) {
                return null;
            }

            customerMap.put(customer.getId(), customer);
            save();

            return customer;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCustomer(int id) throws IOException {

        synchronized (customerMap) {
            // Return false if customer doesn't exist
            if (!customerMap.containsKey(id)) {
                return false;
            }
            // Otherwise remove it and save
            else {
                customerMap.remove(id);
                save();
                return true;

            }
        }
    }
}
