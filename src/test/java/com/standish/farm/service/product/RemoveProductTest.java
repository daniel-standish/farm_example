package com.standish.farm.service.product;

import com.standish.farm.persistence.repositories.ProductRepository;
import com.standish.farm.service.AnimalService;
import com.standish.farm.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class RemoveProductTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(mock(ProductRepository.class), mock(AnimalService.class));
    }

    @Test
    void happyPath() throws Exception {
        productService.removeProduct(10L);
    }

    @Test
    void invalidId() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.removeProduct(-10L),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not remove product. Product ID cannot be negative.", thrown.getMessage());
    }

}
