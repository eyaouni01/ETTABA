package com.intern.backendettaba.designpattern.factory;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Chicken;
import com.intern.backendettaba.entities.Image;

import java.time.LocalDate;
import java.util.Set;

public class ChickenFactory extends AnimalFactory {

    private Integer defaultEggsPerWeek;

    public ChickenFactory() {
        this.defaultEggsPerWeek = 7;
    }

    public ChickenFactory(Integer defaultEggsPerWeek) {
        this.defaultEggsPerWeek = defaultEggsPerWeek;
    }

    @Override
    public Animal createAnimal(String name, Integer age, Float price,
                               String description, Set<Image> images) {
        Chicken chicken = new Chicken();
        chicken.setName(name);
        chicken.setAge(age);
        chicken.setPrice(price);
        chicken.setDescription(description);
        chicken.setImages(images);
        chicken.setEggsPerWeek(defaultEggsPerWeek);
        chicken.setCreationDate(LocalDate.now());
        return chicken;
    }
}