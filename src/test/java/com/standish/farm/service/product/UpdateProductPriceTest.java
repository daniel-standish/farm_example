package com.standish.farm.service.product;

import com.standish.farm.persistence.entities.Product;
import com.standish.farm.persistence.repositories.ProductRepository;
import com.standish.farm.service.AnimalService;
import com.standish.farm.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateProductPriceTest {

    ProductService productService;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository, mock(AnimalService.class));
    }

    @Test
    void happyPath() throws Exception {
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product("Betsy", "Cow", 10000)));
        productService.updateProductPrice(10L, 30000);
    }

    @Test
    void invalidId() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.updateProductPrice(-10L, 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not update product. Product ID cannot be negative.", thrown.getMessage());
    }

    @Test
    void negativePrice() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.updateProductPrice(10L, -30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not update product. Product price cannot be negative.", thrown.getMessage());
    }

    @Test
    void largePrice() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.updateProductPrice(10L, 100000001),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not update product. Product price cannot exceed $1M.", thrown.getMessage());
    }

    @Test
    void productNotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.updateProductPrice(10L, 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not update product. Could not find product 10.", thrown.getMessage());
    }
}
