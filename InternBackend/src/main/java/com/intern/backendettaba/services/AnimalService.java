package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Chicken;
import com.intern.backendettaba.entities.Cow;
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

    @Autowired
    public AnimalService(UserRepository userRepository,
                         FarmRepository farmRepository,
                         AnimalRepository animalRepository) {
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.animalRepository = animalRepository;
    }

    public ResponseEntity<Animal> saveAnimal(Animal animal) {
        animal.setBoughtDate(LocalDate.now());
        animal.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(animalRepository.save(animal), HttpStatus.CREATED);
    }

    public ResponseEntity<Animal> getAnimalByID(Long id){
        Animal animal = animalRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> getAllAnimals(){
        return ResponseEntity.ok(animalRepository.findAll());
    }

    public ResponseEntity<Animal> updateAnimal(Animal newAnimal, Long id){
        Animal dbAnimal = animalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id)));

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

        // Mettre à jour les attributs spécifiques selon le type d'animal
        if (dbAnimal instanceof Chicken && newAnimal instanceof Chicken) {
            Chicken dbChicken = (Chicken) dbAnimal;
            Chicken newChicken = (Chicken) newAnimal;

            if (Objects.nonNull(newChicken.getEggsPerWeek()) &&
                    !Objects.equals(newChicken.getEggsPerWeek(), dbChicken.getEggsPerWeek())) {
                dbChicken.setEggsPerWeek(newChicken.getEggsPerWeek());
            }
        } else if (dbAnimal instanceof Cow && newAnimal instanceof Cow) {
            Cow dbCow = (Cow) dbAnimal;
            Cow newCow = (Cow) newAnimal;

            if (Objects.nonNull(newCow.getMilkPerDay()) &&
                    !Objects.equals(newCow.getMilkPerDay(), dbCow.getMilkPerDay())) {
                dbCow.setMilkPerDay(newCow.getMilkPerDay());
            }
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

    // Nouvelle méthode pour ajouter un animal à une ferme avec polymorphisme
    public ResponseEntity<Animal> addAnimalToFarmById(Long farmId, String animalType, Animal animalRequest) {
        return farmRepository.findById(farmId).map(farm -> {
            Animal newAnimal;

            // Création polymorphique de l'animal en fonction du type
            switch (animalType.toUpperCase()) {
                case "COW":
                    Cow cow = new Cow();
                    cow.setName(animalRequest.getName());
                    cow.setAge(animalRequest.getAge());
                    cow.setPrice(animalRequest.getPrice());
                    cow.setDescription(animalRequest.getDescription());
                    cow.setImages(animalRequest.getImages());
                    cow.setMilkPerDay(20.0f); // Valeur par défaut
                    cow.setCreationDate(LocalDate.now());
                    cow.setFarm(farm);
                    newAnimal = cow;
                    break;

                case "CHICKEN":
                    Chicken chicken = new Chicken();
                    chicken.setName(animalRequest.getName());
                    chicken.setAge(animalRequest.getAge());
                    chicken.setPrice(animalRequest.getPrice());
                    chicken.setDescription(animalRequest.getDescription());
                    chicken.setImages(animalRequest.getImages());
                    chicken.setEggsPerWeek(7); // Valeur par défaut
                    chicken.setCreationDate(LocalDate.now());
                    chicken.setFarm(farm);
                    newAnimal = chicken;
                    break;

                default:
                    throw new IllegalArgumentException("Type d'animal non supporté: " + animalType);
            }

            return new ResponseEntity<>(animalRepository.save(newAnimal), HttpStatus.CREATED);
        }).orElseThrow(() -> new ResourceNotFoundException("Farm not found with id = " + farmId));
    }

    // Version spécifique pour créer directement une vache
    public ResponseEntity<Cow> addCowToFarm(Long farmId, Cow cowRequest) {
        return farmRepository.findById(farmId).map(farm -> {
            cowRequest.setFarm(farm);
            cowRequest.setCreationDate(LocalDate.now());

            // Assigner une valeur par défaut si non fournie
            if (cowRequest.getMilkPerDay() == null) {
                cowRequest.setMilkPerDay(20.0f);
            }

            return new ResponseEntity<>(animalRepository.save(cowRequest), HttpStatus.CREATED);
        }).orElseThrow(() -> new ResourceNotFoundException("Farm not found with id = " + farmId));
    }

    // Version spécifique pour créer directement un poulet
    public ResponseEntity<Chicken> addChickenToFarm(Long farmId, Chicken chickenRequest) {
        return farmRepository.findById(farmId).map(farm -> {
            chickenRequest.setFarm(farm);
            chickenRequest.setCreationDate(LocalDate.now());

            // Assigner une valeur par défaut si non fournie
            if (chickenRequest.getEggsPerWeek() == null) {
                chickenRequest.setEggsPerWeek(7);
            }

            return new ResponseEntity<>(animalRepository.save(chickenRequest), HttpStatus.CREATED);
        }).orElseThrow(() -> new ResourceNotFoundException("Farm not found with id = " + farmId));
    }

    public ResponseEntity<List<Animal>> getAllAnimalsByFarmId(Long farmId){
        if(!farmRepository.existsById(farmId)){
            throw new ResourceNotFoundException("Farm not found with id: " + farmId);
        }
        return new ResponseEntity<>(animalRepository.findByFarmId(farmId), HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> deleteAllAnimalsFromFarmById(Long id){
        if(!farmRepository.existsById(id)){
            throw new ResourceNotFoundException("Farm not found");
        }
        animalRepository.deleteByFarmId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Animal> addAnimalToUserById(Long userId, String animalType, Animal animalRequest) {
        return userRepository.findById(userId).map(user -> {
            Animal newAnimal;

            // Création polymorphique de l'animal en fonction du type
            switch (animalType.toUpperCase()) {
                case "COW":
                    Cow cow = new Cow();
                    cow.setName(animalRequest.getName());
                    cow.setAge(animalRequest.getAge());
                    cow.setPrice(animalRequest.getPrice());
                    cow.setDescription(animalRequest.getDescription());
                    cow.setImages(animalRequest.getImages());
                    cow.setMilkPerDay(20.0f); // Valeur par défaut
                    cow.setCreationDate(LocalDate.now());
                    cow.setUser(user);
                    newAnimal = cow;
                    break;

                case "CHICKEN":
                    Chicken chicken = new Chicken();
                    chicken.setName(animalRequest.getName());
                    chicken.setAge(animalRequest.getAge());
                    chicken.setPrice(animalRequest.getPrice());
                    chicken.setDescription(animalRequest.getDescription());
                    chicken.setImages(animalRequest.getImages());
                    chicken.setEggsPerWeek(7); // Valeur par défaut
                    chicken.setCreationDate(LocalDate.now());
                    chicken.setUser(user);
                    newAnimal = chicken;
                    break;

                default:
                    throw new IllegalArgumentException("Type d'animal non supporté: " + animalType);
            }

            return new ResponseEntity<>(animalRepository.save(newAnimal), HttpStatus.CREATED);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id = " + userId));
    }

    public ResponseEntity<List<Animal>> getAllAnimalsByUserId(Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return new ResponseEntity<>(animalRepository.findByUserId(userId), HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> deleteAllAnimalsFromUserById(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found");
        }
        animalRepository.deleteByUserId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}