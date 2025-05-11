package com.intern.backendettaba.designpattern.factory;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AnimalFactoryRegistry {
    
    private final Map<String, AnimalFactory> factories = new HashMap<>();
    
    @PostConstruct
    public void initialize() {
        // Register default factories
        register(new CowFactory());
        register(new ChickenFactory());
        // Register more factories here as needed
    }
    
    public void register(AnimalFactory factory) {
        factories.put(factory.getAnimalType(), factory);
    }
    
    public AnimalFactory getFactory(String animalType) {
        AnimalFactory factory = factories.get(animalType.toUpperCase());
        if (factory == null) {
            throw new IllegalArgumentException("Unknown animal type: " + animalType);
        }
        return factory;
    }
    
    public Set<String> getAvailableAnimalTypes() {
        return factories.keySet();
    }
    
    public boolean hasFactory(String animalType) {
        return factories.containsKey(animalType.toUpperCase());
    }
}