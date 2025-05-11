
package com.intern.backendettaba.designpattern.factory;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Farm;
import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.entities.User;

import java.time.LocalDate;
import java.util.Set;


public abstract class AnimalFactory {
    

    public Animal createAnimal(String name, Integer age, Float price,
                                 String description, Set<Image> images) {
        // Create the specific animal type
        Animal animal = createSpecificAnimal();
        
        // Setup common attributes - Template Method pattern
        initializeCommonAttributes(animal, name, age, price, description, images);
        
        // Initialize specific attributes for this animal type
        initializeSpecificAttributes(animal);
        
        return animal;
    }
    
    /**
     * Create animal for a specific farm
     */
    public Animal createAnimalForFarm(String name, Integer age, Float price,
                                      String description, Set<Image> images, Farm farm) {
        Animal animal = createAnimal(name, age, price, description, images);
        animal.setFarm(farm);
        return animal;
    }
    

    
    /**
     * Initialize common attributes for all animal types
     */
    protected void initializeCommonAttributes(Animal animal, String name, Integer age, 
                                             Float price, String description, Set<Image> images) {
        animal.setName(name);
        animal.setAge(age);
        animal.setPrice(price);
        animal.setDescription(description);
        animal.setImages(images);
        animal.setCreationDate(LocalDate.now());
        animal.setBoughtDate(LocalDate.now());
    }
    
    
    protected abstract Animal createSpecificAnimal();
    
    protected abstract void initializeSpecificAttributes(Animal animal);
    
    public abstract String getAnimalType();
}