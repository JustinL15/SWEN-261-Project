package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Customer;

/**
 * Interface for saving a Customer to the data store
 * 
 * @author Matt London
 */
public interface CustomerDAO {
    /**
     * Retrieves all {@link Customer customer} from the data store
     * 
     * @return Array of {@link Customer customer} objects, array may be empty
     * @throws IOException If the data store cannot be accessed
     */
    Customer[] getCustomers() throws IOException;

    /**
     * Finds all {@link Customer customer} whose name matches given text
     * 
     * @param name Name to search for
     * @return Array of matching {@link Customer customer}, array may be empty
     * @throws IOException If the data store cannot be accessed
     */
    Customer[] findCustomers(String name) throws IOException;

    /**
     * Retrieves a {@link Customer customer} from the data store
     * 
     * @param id ID of the {@link Customer customer} to get
     * @return {@link Customer customer} or null if not found
     * @throws IOException If the data store cannot be accessed
     */
    Customer getCustomer(int id) throws IOException;

    /**
     * Saves a {@link Customer customer} to the data store
     * 
     * @param customer Created {@link Customer customer} to save
     * @return {@link Customer customer} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Customer createCustomer(Customer customer) throws IOException;

    /**
     * Updates a {@link Customer customer} in the data store
     * 
     * @param customer Updated {@link Cutomer customer} to save
     * @return {@link Customer customer} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Customer updateCustomer(Customer customer) throws IOException;

    /**
     * Deletes a {@link Customer customer} from the data store
     * 
     * @param id ID of the {@link Customer customer} to delete
     * @return True if the ID customer was deleted false if not
     * @throws IOException If the data store cannot be accessed
     */
    boolean deleteCustomer(int id) throws IOException;
}
