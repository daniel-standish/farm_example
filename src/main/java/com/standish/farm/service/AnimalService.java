package com.standish.farm.service;

import com.standish.farm.persistence.entities.Animal;
import com.standish.farm.persistence.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public void createAnimal(String name, String type, int price, int quantity) {
        IntStream.range(0, quantity).forEach(x -> animalRepository.save(new Animal(name, type, price)));
    }

    public int sellAnimal(Long id) {
        int value = 0;
        Optional<Animal> animal = animalRepository.findById(id);
        if(animal.isPresent()) {
            value = animal.get().getValue();
        }
        animalRepository.deleteById(id);
        return value;
    }

}
