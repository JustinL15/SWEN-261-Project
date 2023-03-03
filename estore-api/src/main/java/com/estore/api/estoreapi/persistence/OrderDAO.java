package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Order;

public interface OrderDAO {

    /**
     * Retrieves all {@link Order order} from the data store
     * 
     * @return Array of {@link Order order} objects, array may be empty
     * @throws IOException If the data store cannot be accessed
     */

    Order[] getOrders() throws IOException;

    /**
     * Retrieves {@link Order order} from the data store
     * 
     * @return Array of {@link Order order} objects, array may be empty
     * @throws IOException If the data store cannot be accessed
     */

    Order getOrder(int id) throws IOException;

    /**
     * Finds all {@link Order order} that match the given text
     * 
     * @param text Text that should match all returned
     * @return Array of matching {@link Order order}s, array may be empty
     * @throws IOException If the data store cannot be accessed
     */

    Order[] findOrders(String text) throws IOException;

    /**
     * Saves a {@link Order order} to the data store
     * 
     * @param product Created {@link Order order} to save
     * @return {@link Order order} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */

    Order createOrder(Order order) throws IOException;

    /**
     * Deletes a {@link Order order} from the data store
     * 
     * @param id ID of the {@link Order order} to delete
     * @return True if the ID product was deleted false if not
     * @throws IOException If the data store cannot be accessed
     */

    boolean deleteOrder(int id) throws IOException;
    
}
