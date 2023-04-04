package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ProductReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class CartDAOFileTest {
    CartFileDAO cartFileDAO;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testCarts = new Cart[3];
        testCarts[0] = new Cart(99);
        testCarts[1] = new Cart(100);
        testCarts[2] = new Cart(101);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the cart array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Cart[].class))
                .thenReturn(testCarts);
        cartFileDAO = new CartFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetCarts() {
        // Invoke
        Cart[] carts = cartFileDAO.getCarts();

        // Analyze
        assertEquals(carts.length,testCarts.length);
        for (int i = 0; i < testCarts.length;++i)
            assertEquals(carts[i],testCarts[i]);
    }

    @Test
    public void testGetCart() {
        // Invoke
        Cart cart = cartFileDAO.getCart(99);

        // Analzye
        assertEquals(cart,testCarts[0]);
    }

    @Test
    public void testRemoveItem() throws IOException {
        // Invoke
        Cart cart = cartFileDAO.getCart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky", null, false);
        CartDAO mockCartDAO = mock(CartDAO.class);
        mockCartDAO.addItem(cart.getId(), product.getId(), 1);
        // When createCart is called on the Mock Cart DAO, throw an IOException
        // ProductReference productRef = new ProductReference(80, 1);
        // Analyze
        doThrow(new IOException()).when(mockCartDAO).removeItem(cart.getId(), product.getId());
        boolean removeProduct = cartFileDAO.removeItem(cart.getId(), product.getId());

        Boolean result = assertDoesNotThrow(() -> cartFileDAO.removeItem(cart.getId(), product.getId()),
        "Unexpected exception thrown");

        assertNotNull(result);

        assertEquals(result, removeProduct);
    }

    @Test
    public void testAddItemToCart() throws IOException {
        // Invoke
        Cart cart = cartFileDAO.getCart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky", null, false);
        CartDAO mockCartDAO = mock(CartDAO.class);
        mockCartDAO.addItem(cart.getId(), product.getId(), 1);
        // Analyze
        boolean removeProduct = cartFileDAO.addItem(cart.getId(), product.getId(), 1);

        Boolean result = assertDoesNotThrow(() -> cartFileDAO.removeItem(cart.getId(), product.getId()),
        "Unexpected exception thrown");

        assertNotNull(result);

        assertEquals(result, removeProduct);
    }

    @Test
    public void testEditQuantity() throws IOException {
        // Invoke
        Cart cart = cartFileDAO.getCart(99);
        Product product = new Product(80, "tree tea coffee", 3, 10, "a little barky", null, false);
        CartDAO mockCartDAO = mock(CartDAO.class);
        mockCartDAO.addItem(cart.getId(), product.getId(), 1);
        // Analyze
        boolean editQuan = cartFileDAO.addItem(cart.getId(), product.getId(), 1);
        
        editQuan = cartFileDAO.editQuantity(cart.getId(), product.getId(), 1);
        Boolean result = assertDoesNotThrow(() -> cartFileDAO.editQuantity(cart.getId(), product.getId(), 2),
        "Unexpected exception thrown");

        assertNotNull(result);

        assertEquals(result, editQuan);
    }

    @Test
    public void testDeleteCart() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> cartFileDAO.deleteCart(99),"Unexpected exception thrown");
        

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test carts array - 1 (because of the delete)
        // Because carts attribute of CartFileDAO is package private
        // we can access it directly
        assertEquals(cartFileDAO.getCarts().length,testCarts.length-1);
    }

    @Test
    public void testCreateCart() {
        // Setup
        Cart cart = new Cart(102);

        // Invoke
        Cart result = assertDoesNotThrow(() -> cartFileDAO.createCart(cart),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Cart actual = cartFileDAO.getCart(cart.getId());
        assertEquals(actual.getId(),cart.getId());
    }

    @Test
    public void testUpdateCart() {
        // Setup
        Cart cart = new Cart(99);

        // Invoke
        Cart result = assertDoesNotThrow(() -> cartFileDAO.updateCart(cart),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Cart actual = cartFileDAO.getCart(cart.getId());
        assertEquals(actual,cart);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Cart[].class));

        Cart cart = new Cart(102);

        assertThrows(IOException.class,
                        () -> cartFileDAO.createCart(cart),
                        "IOException not thrown");
    }

    @Test
    public void testGetCartNotFound() {
        // Invoke
        Cart cart = cartFileDAO.getCart(98);

        // Analyze
        assertEquals(cart,null);
    }

    @Test
    public void testDeleteCartNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> cartFileDAO.deleteCart(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(cartFileDAO.getCarts().length,testCarts.length);
    }

    @Test
    public void testUpdateCartNotFound() {
        // Setup
        Cart cart = new Cart(98);

        // Invoke
        Cart result = assertDoesNotThrow(() -> cartFileDAO.updateCart(cart),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the CartFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Cart[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new CartFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
