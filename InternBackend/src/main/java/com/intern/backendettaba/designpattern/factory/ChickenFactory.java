package com.intern.backendettaba.designpattern.factory;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Chicken;

public class ChickenFactory extends AnimalFactory {
    private Integer eggsPerWeek;
    
    public ChickenFactory() {
        this.eggsPerWeek = 7;
    }
    
    public ChickenFactory(Integer eggsPerWeek) {
        this.eggsPerWeek = eggsPerWeek;
    }
    
    @Override
    protected Animal createSpecificAnimal() {
        return new Chicken();
    }
    
    @Override
    protected void initializeSpecificAttributes(Animal animal) {
        if (animal instanceof Chicken) {
            ((Chicken) animal).setEggsPerWeek(eggsPerWeek);
        }
    }
    
    @Override
    public String getAnimalType() {
        return "CHICKEN";
    }
    
    public void setEggsPerWeek(Integer eggsPerWeek) {
        this.eggsPerWeek = eggsPerWeek;
    }
}

