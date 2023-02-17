package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Product;

/**
 * Interface for saving a Product to a data store
 * 
 * @author Matt London
 */
public interface ProductDAO {
    /**
     * Retrieves all {@link Product product} from the data store
     * 
     * @return Array of {@link Product product} objects, array may be empty
     * @throws IOException If the data store cannot be accessed
     */
    Product[] getProducts() throws IOException;

    /**
     * Finds all {@link Product product} that match the given text
     * 
     * @param text Text that should match all returned
     * @return Array of matching {@link Product product}s, array may be empty
     * @throws IOException If the data store cannot be accessed
     */
    Product[] findProducts(String text) throws IOException;

    /**
     * Retrieves a {@link Product product} from the data store
     * 
     * @param id ID of the {@link Product product} desired
     * @return {@link Product product} or null if not found
     * @throws IOException If the data store cannot be accessed
     */
    Product getProduct(int id) throws IOException;

    /**
     * Saves a {@link Product product} to the data store
     * 
     * @param product Created {@link Product product} to save
     * @return {@link Product product} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Product createProduct(Product product) throws IOException;

    /**
     * Updates a {@link Product product} in the data store
     * 
     * @param product Updated {@link Product product} to save
     * @return {@link Product product} (null if not able to be updated)
     * @throws IOException If the data store cannot be accessed
     */
    Product updateProduct(Product product) throws IOException;

    /**
     * Deletes a {@link Product product} from the data store
     * 
     * @param id ID of the {@link Product product} to delete
     * @return True if the ID product was deleted false if not
     * @throws IOException If the data store cannot be accessed
     */
    boolean deleteProduct(int id) throws IOException;
}
