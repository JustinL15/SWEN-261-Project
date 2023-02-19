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

import com.estore.api.estoreapi.model.Product;

/**
 * Implementation of ProductDAO which will save to a file as a data store
 * 
 * @author Matt London
 */
@Component
public class ProductFileDAO  implements ProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductFileDAO.class.getName());
    
    /** Maps ids to their corresponding {@link Product} */
    Map<Integer, Product> productMap;

    /** JSON serializer/deserializer */
    private ObjectMapper objectMapper;

    /** Next id to assign */
    private static int nextId;

    private String filename;

    /**
     * Build the {@link ProductFileDAO}
     * 
     * @param filename Filename to store the data in
     * @param objectMapper JSON serializer/deserializer
     * 
     * @throws IOException If there is an error reading the file
     */
    public ProductFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
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
     * Gets array of {@link Product products} from the file
     * 
     * @return Array of stored {@link Product products}, or empty array if none
     */
    private Product[] getProductsArray() {
        return searchProducts(null);
    }

    /**
     * Searches through the array of {@link Product} for those matching some text
     * 
     * @param matching Text to match
     * @return Matching {@link Product products}, or empty array if none
     */
    private Product[] searchProducts(String matching) {
        List<Product> matchingProducts = new ArrayList<>();

        for (Product product : productMap.values()) {
            // If no text, matching name, or matching description then add it
            if (matching == null || (product.getName() != null && product.getName().contains(matching))
                    || (product.getDescription() != null && product.getDescription().contains(matching))) {
                        matchingProducts.add(product);
            }
        }

        Product[] productArray = new Product[matchingProducts.size()];
        matchingProducts.toArray(productArray);

        return productArray;
    }

    /**
     * Saves {@link Product} to the DAO file
     * 
     * @throws IOException If there is an error writing to the file
     */
    private void save() throws IOException{
        Product[] allProducts = getProductsArray();

        // Uses object mapper to convert to json and write to file
        objectMapper.writeValue(new File(filename), allProducts);
    }

    /**
     * Load all {@link Product} from the DAO file and store in the map
     * 
     * @throws IOException If there is an error reading the file
     */
    private void load() throws IOException {
        productMap = new TreeMap<>();
        nextId = 0;

        Product[] serializedProducts = objectMapper.readValue(new File(filename), Product[].class);

        // Add all products and keep track of the greatest id
        for (Product product: serializedProducts) {
            productMap.put(product.getId(), product);
            if (product.getId() > nextId) {
                nextId = product.getId();
            }
        }

        // Incremement id again so it is ready to return the next id
        getNextId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product[] getProducts() {
        // Synchronize on productMap so that it can't be modified while being read
        synchronized(productMap) {
            return getProductsArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product[] findProducts(String matching) {
        synchronized(productMap) {
            return searchProducts(matching);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) {
        synchronized(productMap) {
            return productMap.get(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized(productMap) {
            Product tmpProd = new Product(getNextId(), product.getName(), product.getPrice(),
                                            product.getQuantity(), product.getDescription());
            
            // Add to map and save to DAO
            productMap.put(tmpProd.getId(), tmpProd);
            save();

            return tmpProd;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized(productMap) {
            if (!productMap.containsKey(product.getId())) {
                return null;
            }

            productMap.put(product.getId(), product);
            save();

            return product;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized (productMap) {
            if (!productMap.containsKey(id)) {
                return false;
            }
            else {
                productMap.remove(id);
                save();
                return true;
            }
        }
    }
}
