package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Cart;

/**
 * Interface for saving cart to a data store
 * 
 * @author Matt London
 */
public interface CartDAO {
    /**
     * Retrieves all {@link Cart cart} from the data store
     * 
     * @return Array of {@link Cart cart} objects, array may be empty, but will not be null
     * @throws IOException If the data store cannot be accessed
     */
    Cart[] getCarts() throws IOException;

    /**
     * Retrieves a {@link Cart cart} from the file
     * 
     * @param id ID of the {@link Cart cart} corresponding to the user
     * @return {@link Cart cart} or null if not found
     * @throws IOException If the data store cannot be accessed
     */
    Cart getCart(int id) throws IOException;

    /**
     * Adds an item to the cart
     * 
     * @param id Id of the cart
     * @param productId Id of the product
     * @param quantity Quantity of the product
     * @throws IOException If the data store cannot be accessed
     */
    void addItem(int id, int productId, int quantity) throws IOException;

    /**
     * Removes an item from the cart
     * 
     * @param id Id of the cart
     * @param productId Id of the product
     * @throws IOException If the data store cannot be accessed
     */
    void removeItem(int id, int productId) throws IOException;

    /**
     * Edits the quantity of an item in the cart
     * 
     * @param id Id of the cart
     * @param productId Id of the product
     * @param quantity Quantity of the product
     * @throws IOException If the data store cannot be accessed
     */
    void editQuantity(int id, int productId, int quantity) throws IOException;

    /**
     * Saves a {@link Cart cart} to the data store
     * 
     * @param cart Created {@link Cart cart} to save
     * @return {@link Cart cart} (null if not found)
     * @throws IOException If the data store cannot be accessed
     */
    Cart createCart(Cart cart) throws IOException;

    /**
     * Deletes a {@link Cart cart} from the data store
     * 
     * @param id ID of the {@link Cart cart} to delete
     * @return True if the ID {@link Cart} was deleted false if not
     * @throws IOException If the data store cannot be accessed
     */
    boolean deleteCart(int id) throws IOException;
}
