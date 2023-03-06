package com.estore.api.estoreapi.persistence;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Controller-tier")
@SpringBootTest
public class CartDAOFileTest {

    @Test
    void testCartFileDAO(){
        CartFileDAO cFileDAO = new CartFileDAO("products", null);
    }
    
    @Test
    void testGetCartsArray(){
        
    }
}
