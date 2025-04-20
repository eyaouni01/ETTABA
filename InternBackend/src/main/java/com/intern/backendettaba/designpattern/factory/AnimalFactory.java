package com.intern.backendettaba.designpattern.factory;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Farm;
import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.entities.User;

import java.time.LocalDate;
import java.util.Set;

public abstract class AnimalFactory {

    public abstract Animal createAnimal(String name, Integer age, Float price,
                                        String description, Set<Image> images);

    public Animal createAnimalForFarm(String name, Integer age, Float price,
                                      String description, Set<Image> images, Farm farm) {
        Animal animal = createAnimal(name, age, price, description, images);
        animal.setFarm(farm);
        animal.setCreationDate(LocalDate.now());
        return animal;
    }

    public Animal createAnimalForUser(String name, Integer age, Float price,
                                      String description, Set<Image> images, User user) {
        Animal animal = createAnimal(name, age, price, description, images);
        animal.setUser(user);
        animal.setCreationDate(LocalDate.now());
        return animal;
    }
}