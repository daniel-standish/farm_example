package com.standish.farm.service.product;

import com.standish.farm.persistence.repositories.ProductRepository;
import com.standish.farm.service.ProductService;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateProductTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(mock(ProductRepository.class));
    }

    @Test
    void happyPath() throws Exception {
        productService.createProduct("Bob", "Cow", 30000);
    }

    @Test
    void emptyName() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct("", "Cow", 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product name is empty or null.", thrown.getMessage());
    }

    @Test
    void nullName() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct(null, "Cow", 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product name is empty or null.", thrown.getMessage());
    }

    @Test
    void longName() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct(RandomStringUtils.random(257, true, false), "Cow", 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product name is longer than 256 characters.", thrown.getMessage());
    }

    @Test
    void invalidName() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct(RandomStringUtils.random(100, true, true), "Cow", 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product name must only contain letters.", thrown.getMessage());
    }

    @Test
    void emptyType() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct("Betsy", "", 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product type is empty or null.", thrown.getMessage());
    }

    @Test
    void nullType() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct("Betsy", null, 30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product type is empty or null.", thrown.getMessage());
    }

    @Test
    void negativePrice() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct("Betsy", "Cow", -30000),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product price cannot be negative.", thrown.getMessage());
    }

    @Test
    void largePrice() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.createProduct("Betsy", "Cow", 100000001),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not create product. Product price cannot exceed $1M.", thrown.getMessage());
    }
}