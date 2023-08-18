package com.standish.farm.service.product;

import com.standish.farm.persistence.repositories.ProductRepository;
import com.standish.farm.service.AnimalService;
import com.standish.farm.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SellProductTest {

    ProductService productService;
    AnimalService animalService;

    @BeforeEach
    void setUp() {
        animalService = mock(AnimalService.class);
        productService = new ProductService(mock(ProductRepository.class), animalService);
    }

    @Test
    void happyPath() throws Exception {
        when(animalService.getAnimalTypes()).thenReturn(List.of("Sheep", "Cow", "Pig", "Chicken"));
        productService.sellProduct(10L, "Cow");
    }

    @Test
    void invalidId() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.sellProduct(-10L, "Cow"),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not sell product. Product ID cannot be negative.", thrown.getMessage());
    }

    @Test
    void emptyType() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.sellProduct(10L, ""),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not sell product. Product type is empty or null.", thrown.getMessage());
    }

    @Test
    void nullType() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.sellProduct(10L, null),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not sell product. Product type is empty or null.", thrown.getMessage());
    }

    @Test
    void productNotAnimal() {
        when(animalService.getAnimalTypes()).thenReturn(List.of("Sheep", "Cow", "Pig", "Chicken"));
        Exception thrown = assertThrows(
                Exception.class,
                () -> productService.sellProduct(10L, "Horse"),
                "Expected exception that wasn't thrown."
        );
        assertEquals("Could not sell product. Cannot sell a product of type Horse.", thrown.getMessage());
    }
}
