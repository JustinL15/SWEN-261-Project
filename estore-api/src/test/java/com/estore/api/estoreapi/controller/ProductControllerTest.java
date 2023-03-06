package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Product Controller class
 * 
 * @author Alexis Sanders
 */
@Tag("Controller-tier")
public class ProductControllerTest {
    private ProductController productController;
    private ProductDAO mockProductDAO;

    /**
     * Before each test, create a new ProductController object and inject
     * a mock Product DAO
     */
    @BeforeEach
    public void setupProductController() {
        mockProductDAO = mock(ProductDAO.class);
        productController = new ProductController(mockProductDAO);
    }

    @Test
    public void testGetProduct() throws IOException {  // getProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee", 2.00, 2, "good drink");
        // When the same id is passed in, our mock Product DAO will return the Product object
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = productController.getProduct(product.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testGetProductNotFound() throws Exception { // createProduct may throw IOException
        // Setup
        int productId = 99;
        // When the same id is passed in, our mock Product DAO will return null, simulating
        // no product found
        when(mockProductDAO.getProduct(productId)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.getProduct(productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetProductHandleException() throws Exception { // createProduct may throw IOException
        // Setup
        int productId = 99;
        // When getProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).getProduct(productId);

        // Invoke
        ResponseEntity<Product> response = productController.getProduct(productId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    /*****************************************************************
     * The following tests will fail until all ProductController methods
     * are implemented.
     ****************************************************************/

    @Test
    public void testCreateProduct() throws IOException {  // createProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee beans", 3.00, 4, "make coffee");
        // when createProduct is called, return true simulating successful
        // creation and save
        when(mockProductDAO.createProduct(product)).thenReturn(product);

        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testCreateProductFailed() throws IOException {  // createProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee cup", 5.00, 1, "drink out of");
        Product newProduct = new Product(99, "coffee cup", 5.00, 1, "drink out of");

        // when createProduct is called, return false simulating failed
        // creation and save
        when(mockProductDAO.createProduct(product)).thenReturn(product);

        when(mockProductDAO.createProduct(newProduct)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.createProduct(newProduct);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateProductHandleException() throws IOException {  // createProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee mug", 3.50, 5, "keeps drink warm");

        // When createProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).createProduct(product);

        // Invoke
        ResponseEntity<Product> response = productController.createProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateProduct() throws IOException { // updateProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee cream", 2.00, 2, "enhance flavor");
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockProductDAO.updateProduct(product)).thenReturn(product);
        ResponseEntity<Product> response = productController.updateProduct(product);
        product.setName("coffee creamer");

        // Invoke
        response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(product,response.getBody());
    }

    @Test
    public void testUpdateProductFailed() throws IOException { // updateProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee mug", 3.50, 5, "keeps drink warm");
        // when updateProduct is called, return true simulating successful
        // update and save
        when(mockProductDAO.updateProduct(product)).thenReturn(null);

        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateProductHandleException() throws IOException { // updateProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee mug", 3.50, 5, "keeps drink warm");
        // When updateProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).updateProduct(product);

        // Invoke
        ResponseEntity<Product> response = productController.updateProduct(product);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetProducts() throws IOException { // getProductes may throw IOException
        // Setup
        Product[] products = new Product[2];
        products[0] = new Product(99, "coffee mug", 3.50, 5, "keeps drink warm");
        products[1] = new Product(100, "coffee cup", 5.00, 1, "drink out of");
        // When getProductes is called return the products created above
        when(mockProductDAO.getProducts()).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.getProducts();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testGetProductesHandleException() throws IOException { // getProductes may throw IOException
        // Setup
        // When getProductes is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).getProducts();

        // Invoke
        ResponseEntity<Product[]> response = productController.getProducts();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchProductes() throws IOException { // findProductes may throw IOException
        // Setup
        String searchString = "ea";
        Product[] products = new Product[2];
        products[0] = new Product(99, "coffee beans", 3.00, 4, "make coffee");
        products[1] = new Product(100, "coffee cream", 2.00, 2, "enhance flavor");
        // When findProductes is called with the search string, return the two
        /// products above
        when(mockProductDAO.findProducts(searchString)).thenReturn(products);

        // Invoke
        ResponseEntity<Product[]> response = productController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(products,response.getBody());
    }

    @Test
    public void testSearchProductesHandleException() throws IOException { // findProductes may throw IOException
        // Setup
        String searchString = "an";
        // When createProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).findProducts(searchString);

        // Invoke
        ResponseEntity<Product[]> response = productController.searchProducts(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteProduct() throws IOException { // deleteProduct may throw IOException
        // Setup
        Product product = new Product(99, "coffee", 2.00, 2, "good drink");
        int productId = 99;
        
        // When the same id is passed in, our mock Product DAO will return the Product object
        when(mockProductDAO.getProduct(product.getId())).thenReturn(product);
        
        // when deleteProduct is called return true, simulating successful deletion
        when(mockProductDAO.deleteProduct(productId)).thenReturn(true);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteProductNotFound() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 99;
        // when deleteProduct is called return false, simulating failed deletion
        when(mockProductDAO.deleteProduct(productId)).thenReturn(false);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteProductHandleException() throws IOException { // deleteProduct may throw IOException
        // Setup
        int productId = 99;
        // When deleteProduct is called on the Mock Product DAO, throw an IOException
        doThrow(new IOException()).when(mockProductDAO).deleteProduct(productId);

        // Invoke
        ResponseEntity<Product> response = productController.deleteProduct(productId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
