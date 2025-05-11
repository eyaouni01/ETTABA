// 3. Cow Factory Implementation

package com.intern.backendettaba.designpattern.factory;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Cow;

public class CowFactory extends AnimalFactory {
    private Float milkPerDay;
    
    public CowFactory() {
        this.milkPerDay = 20.0f;
    }
    
    public CowFactory(Float milkPerDay) {
        this.milkPerDay = milkPerDay;
    }
    
    @Override
    protected Animal createSpecificAnimal() {
        return new Cow();
    }
    
    @Override
    protected void initializeSpecificAttributes(Animal animal) {
        if (animal instanceof Cow) {
            ((Cow) animal).setMilkPerDay(milkPerDay);
        }
    }
    
    @Override
    public String getAnimalType() {
        return "COW";
    }
    
    public void setMilkPerDay(Float milkPerDay) {
        this.milkPerDay = milkPerDay;
    }
}