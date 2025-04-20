package com.intern.backendettaba.designpattern.factory;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Cow;
import com.intern.backendettaba.entities.Image;

import java.time.LocalDate;
import java.util.Set;

public class CowFactory extends AnimalFactory {

    private Float defaultMilkPerDay;

    public CowFactory() {
        this.defaultMilkPerDay = 20.0f;
    }

    public CowFactory(Float defaultMilkPerDay) {
        this.defaultMilkPerDay = defaultMilkPerDay;
    }

    @Override
    public Animal createAnimal(String name, Integer age, Float price,
                               String description, Set<Image> images) {
        Cow cow = new Cow();
        cow.setName(name);
        cow.setAge(age);
        cow.setPrice(price);
        cow.setDescription(description);
        cow.setImages(images);
        cow.setMilkPerDay(defaultMilkPerDay);
        cow.setCreationDate(LocalDate.now());
        return cow;
    }
}