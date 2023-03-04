package com.estore.api.estoreapi.persistence;

import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.util.TreeMap;

import com.estore.api.estoreapi.model.Cart;

/**
 * Implementation of CartFileDAO which will save to a file as a data store
 * 
 * @author Matt London
 */
@Component
public class CartFileDAO  implements CartDAO {
    private static final Logger LOG = Logger.getLogger(CartFileDAO.class.getName());

    /** JSON serializer/deserializer */
    private ObjectMapper objectMapper;

    /** Maps ids to their corresponding {@link Product} */
    Map<Integer, Cart> cartMap;

    /** Next id to assign */
    private static int nextId;

    private String filename;

    /**
     * Build the {@link CartFileDAO}
     * 
     * @param filename Filename to store the data in
     * @param objectMapper JSON serializer/deserializer
     * 
     * @throws IOException If there is an error reading the file
     */
    public CartFileDAO(@Value("${carts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.cartMap = new TreeMap<>();
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
     * Gets array of {@link Cart carts} from the file
     * 
     * @return Array of stored {@link Cart carts}, or empty array if none
     */
    private Cart[] getCartsArray() {
        Cart[] cartArray = new Cart[cartMap.size()];
        cartMap.values().toArray(cartArray);

        return cartArray;
    }

    /**
     * Saves {@link Cart} to the DAO file
     * 
     * @throws IOException If there is an error writing to the file
     */
    private void save() throws IOException{
        Cart[] allCarts = getCartsArray();

        // Uses object mapper to convert to json and write to file
        objectMapper.writeValue(new File(filename), allCarts);
    }

    /**
     * Load all {@link Cart} from the DAO file and store in the map
     * 
     * @throws IOException If there is an error reading the file
     */
    private void load() throws IOException {
        cartMap.clear();
        nextId = 0;

        Cart[] serializedCarts = objectMapper.readValue(new File(filename), Cart[].class);

        // Add all carts and keep track of the greatest id
        for (Cart cart: serializedCarts) {
            cartMap.put(cart.getId(), cart);
            if (cart.getId() > nextId) {
                nextId = cart.getId();
            }
        }

        // Incremement id again so it is ready to return the next id
        getNextId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart[] getCarts() {
        synchronized(cartMap) {
            return getCartsArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart getCart(int id) {
        synchronized(cartMap) {
            return cartMap.get(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart createCart(Cart cart) throws IOException {
        synchronized(cartMap) {
            Cart tmpCart = new Cart(getNextId());
            
            // Add to map and save to DAO
            cartMap.put(tmpCart.getId(), tmpCart);
            save();

            return tmpCart;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCart(int id) throws IOException {
        synchronized (cartMap) {
            if (!cartMap.containsKey(id)) {
                return false;
            }
            else {
                cartMap.remove(id);
                save();
                return true;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cart updateCart(Cart cart) throws IOException {
        synchronized (cartMap) {
            if (!cartMap.containsKey(cart.getId())) {
                return null;
            }
            else {
                cartMap.put(cart.getId(), cart);
                save();
                return cart;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addItem(int cartId, int productId, int quantity) throws IOException {
        synchronized (cartMap) {
            Cart cart = cartMap.get(cartId);
            if (cart == null) {
                return false;
            }
            cart.addItem(productId, quantity);
            save();
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeItem(int cartId, int productId) throws IOException {
        synchronized (cartMap) {
            Cart cart = cartMap.get(cartId);
            if (cart == null) {
                return false;
            }
            cart.removeItem(productId);
            save();
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean editQuantity(int cartId, int productId, int quantity) throws IOException {
        synchronized (cartMap) {
            Cart cart = cartMap.get(cartId);
            if (cart == null) {
                return false;
            }
        
            cart.editQuantity(productId, quantity);
            save();
            return true;
        }
    }
}
