package com.standish.farm.service;

import com.standish.farm.persistence.entities.Animal;
import com.standish.farm.persistence.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    private final List<String> animalTypes = List.of("Sheep", "Cow", "Pig", "Chicken");

    public void createAnimal(String name, String type, int price, int quantity) {
        IntStream.range(0, quantity).forEach(x -> animalRepository.save(new Animal(name, type, price)));
    }

    public int sellAnimal(Long id) throws Exception {
        int value = 0;
        Optional<Animal> animal = animalRepository.findById(id);
        if(animal.isPresent()) {
            value = animal.get().getValue();
        } else {
            throw new Exception("Could not find Animal with ID " + id + ".");
        }
        animalRepository.deleteById(id);
        return value;
    }

    public List<String> getAnimalTypes() {
        return animalTypes;
    }

}
