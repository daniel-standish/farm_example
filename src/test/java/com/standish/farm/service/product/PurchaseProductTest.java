package com.standish.farm.service.product;

import com.standish.farm.persistence.entities.Product;
import com.standish.farm.persistence.repositories.ProductRepository;
import com.standish.farm.service.AnimalService;
import com.standish.farm.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PurchaseProductTest {

    ProductService productService;
    AnimalService animalService;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        animalService = mock(AnimalService.class);
        productService = new ProductService(productRepository, animalService);
    }

    @Test
    void happyPath() throws Exception {
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product("Betsy", "Cow", 10000)));
        when(animalService.getAnimalTypes()).thenReturn(List.of("Sheep", "Cow", "Pig", "Chicken"));
        productService.purchaseProduct(10L, 10);
    }

    @Test
    void invalidId() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.purchaseProduct(-10L, 10),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not purchase product. Product ID cannot be negative.", thrown.getMessage());
    }

    @Test
    void negativeQuantity() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.purchaseProduct(10L, -10),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not purchase product. Purchase quantity cannot be negative.", thrown.getMessage());
    }

    @Test
    void largeQuantity() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.purchaseProduct(10L, 101),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not purchase product. Purchase quantity cannot exceed 100.", thrown.getMessage());
    }

    @Test
    void productNotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.purchaseProduct(10L, 10),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not purchase product. Could not find product 10.", thrown.getMessage());
    }

    @Test
    void productNotAnimal() {
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product("Betsy", "Horse", 10000)));
        when(animalService.getAnimalTypes()).thenReturn(List.of("Sheep", "Cow", "Pig", "Chicken"));
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.purchaseProduct(10L, 10),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not purchase product. Cannot purchase a product of type Horse.", thrown.getMessage());
    }
}
