package com.standish.farm.service;

import com.standish.farm.persistence.entities.Product;
import com.standish.farm.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    private AnimalService animalService;

    private final List<String> animalTypes = List.of("Sheep", "Cow", "Pig", "Chicken");

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(final String name, final String type, final int price) throws Exception {
        try {
            validateProductName(name);
            validateProductType(type);
            validateProductPrice(price);
            productRepository.save(new Product(name, type, price));
        } catch (Exception e) {
            throw new Exception("Could not create product. " + e.getMessage());
        }
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public void removeProduct(final Long id) throws Exception {
        try {
            validateProductId(id);
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Could not remove product. " + e.getMessage());
        }
    }

    public void updateProductPrice(final Long id, final int price) throws Exception {
        try {
            validateProductId(id);
            validateProductPrice(price);
            Optional<Product> item = productRepository.findById(id);
            if (item.isPresent()) {
                Product product = item.get();
                product.updatePrice(price);
                productRepository.save(product);
            } else {
                throw new Exception("Could not find product " + id + ".");
            }
        } catch (Exception e) {
            throw new Exception("Could not update product. " + e.getMessage());
        }
    }

    public void purchaseProduct(final Long id, final int quantity) throws Exception {
        Optional<Product> item = productRepository.findById(id);
        if(item.isPresent()) {
            Product product = item.get();

            if(animalTypes.contains(product.getType())) {
                animalService.createAnimal(product.getName(), product.getType(), product.getPrice(), quantity);
            } else {
                throw new Exception("Cannot purchase a product of type " + product.getType());
            }
        }
    }

    public int sellProduct(final Long id, final String type) {
        if(animalTypes.contains(type)) {
            return animalService.sellAnimal(id);
        }
        return 0;
    }

    private void validateProductName(final String name) throws Exception {
        if(name == null || name.isBlank()) {
            throw new Exception("Product name is empty or null.");
        }
        if(name.length() > 256) {
            throw new Exception("Product name is longer than 256 characters.");
        }
        if(!name.matches("^[a-zA-Z]+$")) {
            throw new Exception("Product name must only contain letters.");
        }
    }

    private void validateProductType(final String type) throws Exception {
        if(type == null || type.isBlank()) {
            throw new Exception("Product type is empty or null.");
        }
        if(!animalTypes.contains(type)) {
            throw new Exception("Product type is invalid. Product type must be one of the following: " + animalTypes.toString());
        }
    }

    private void validateProductPrice(final int price) throws Exception {
        if(price < 0) {
            throw new Exception("Product price cannot be negative.");
        }
        if(price > 100000000) {
            throw new Exception("Product price cannot exceed $1M.");
        }
    }

    private void validateProductId(final Long id) throws Exception {
        if(id < 0) {
            throw new Exception("Product ID cannot be negative.");
        }
    }

}
