package com.intern.backendettaba.services;

import com.intern.backendettaba.designpattern.factory.AnimalFactoryRegistry;
import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.repositories.AnimalRepository;
import com.intern.backendettaba.repositories.FarmRepository;
import com.intern.backendettaba.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AnimalService {

    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final AnimalRepository animalRepository;
    private final AnimalFactoryRegistry factoryRegistry;

    @Autowired
    public AnimalService(UserRepository userRepository,
                         FarmRepository farmRepository,
                         AnimalRepository animalRepository,
                         AnimalFactoryRegistry factoryRegistry) {
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.animalRepository = animalRepository;
        this.factoryRegistry = factoryRegistry;
    }

    public ResponseEntity<Animal> saveAnimal(Animal animal) {
        animal.setBoughtDate(LocalDate.now());
        animal.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(animalRepository.save(animal), HttpStatus.CREATED);
    }
    
    public ResponseEntity<Animal> getAnimalByID(Long id){
        Animal animal = animalRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + id));
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> getAllAnimals(){
        return ResponseEntity.ok(animalRepository.findAll());
    }

    public ResponseEntity<Animal> updateAnimal(Animal newAnimal, Long id){
        Animal dbAnimal = animalRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Animal not found with id: " + id));
            
        if(Objects.nonNull(newAnimal.getName()) && !Objects.equals(newAnimal.getName(), dbAnimal.getName())){
            dbAnimal.setName(newAnimal.getName());
        }
        
        if(Objects.nonNull(newAnimal.getPrice()) && !Objects.equals(newAnimal.getPrice(), dbAnimal.getPrice())){
            dbAnimal.setPrice(newAnimal.getPrice());
        }
        
        if(Objects.nonNull(newAnimal.getDescription()) && !Objects.equals(newAnimal.getDescription(), dbAnimal.getDescription())){
            dbAnimal.setDescription(newAnimal.getDescription());
        }
        
        if(Objects.nonNull(newAnimal.getAge()) && !Objects.equals(newAnimal.getAge(), dbAnimal.getAge())){
            dbAnimal.setAge(newAnimal.getAge());
        }
        
        return new ResponseEntity<>(animalRepository.save(dbAnimal), HttpStatus.OK);
    }

    public ResponseEntity<Animal> deleteAnimal(Long id){
        Optional<Animal> animal = animalRepository.findById(id);
        if(animal.isPresent()) {
            animalRepository.deleteById(id);
            return ResponseEntity.ok(animal.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Add an animal to a farm using the factory pattern
     */
    public ResponseEntity<Animal> addAnimalToFarmById(Long farmId, String animalType, Animal animalRequest) {
        try {
            // Get the appropriate factory from the registry
            var factory = factoryRegistry.getFactory(animalType);
            
            Animal animal = farmRepository.findById(farmId).map(farm -> {
                // Create the animal using farm's createAnimal method (GRASP Creator)
                Animal newAnimal = farm.createAnimal(
                        factory,
                        animalRequest.getName(),
                        animalRequest.getAge(),
                        animalRequest.getPrice(),
                        animalRequest.getDescription(),
                        animalRequest.getImages()
                );

                return animalRepository.save(newAnimal);
            }).orElseThrow(() -> new ResourceNotFoundException("Farm not found with id: " + farmId));

            return new ResponseEntity<>(animal, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid animal type: " + animalType + 
                " - Available types: " + String.join(", ", factoryRegistry.getAvailableAnimalTypes()));
        }
    }
    
    public ResponseEntity<List<Animal>> getAllAnimalsByFarmId(Long farmId){
        if(!farmRepository.existsById(farmId)){
            throw new ResourceNotFoundException("Farm not found with id: " + farmId);
        }

        return new ResponseEntity<>(animalRepository.findByFarmId(farmId), HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> deleteAllAnimalsFromFarmById(Long id){
        if(!farmRepository.existsById(id)){
            throw new ResourceNotFoundException("Farm not found with id: " + id);
        }

        animalRepository.deleteByFarmId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

   
   
}