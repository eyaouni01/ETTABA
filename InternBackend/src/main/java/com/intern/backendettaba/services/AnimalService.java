package com.intern.backendettaba.services;

import com.intern.backendettaba.designpattern.factory.AnimalFactory;
import com.intern.backendettaba.designpattern.factory.ChickenFactory;
import com.intern.backendettaba.designpattern.factory.CowFactory;
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
// Supprimez cette annotation qui génère automatiquement un constructeur
// @RequiredArgsConstructor
public class AnimalService {

    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final AnimalRepository animalRepository;
    // Factory registry
    private final Map<String, AnimalFactory> animalFactories = new HashMap<>();

    // Gardez uniquement ce constructeur
    @Autowired
    public AnimalService(UserRepository userRepository,
                         FarmRepository farmRepository,
                         AnimalRepository animalRepository) {
        this.userRepository = userRepository;
        this.farmRepository = farmRepository;
        this.animalRepository = animalRepository;

        // Register factories
        animalFactories.put("COW", new CowFactory());
        animalFactories.put("CHICKEN", new ChickenFactory());
    }

    public ResponseEntity<Animal> saveAnimal(Animal animal) {
        // Pour être modifié selon l'action de l'utilisateur
        animal.setBoughtDate(LocalDate.now());
        animal.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(animalRepository.save(animal), HttpStatus.CREATED);
    }
    public ResponseEntity<Animal> getAnimalByID(Long id){
        /*
        Optional<Animal> animal = animalRepository.findById(id);
        if(animal.isPresent()) {
            Animal animal1=animal.get();
            animal1.setProfit(Math.abs(animal1.getBoughtPrice()- animal1.getSoldPrice()));
            return ResponseEntity.ok(animal);
        }
        else return ResponseEntity.notFound().build();
        
         */
        Animal animal=animalRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ResponseEntity<>(animal, HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> getAllAnimals(){
        return ResponseEntity.ok(animalRepository.findAll());
    }

    public ResponseEntity<Animal> updateAnimal(Animal newAnimal,Long id){
        Animal dbAnimal=animalRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.valueOf(id)));
        if(Objects.nonNull(newAnimal.getName()) && !Objects.equals(newAnimal.getName(),dbAnimal.getName())){
            dbAnimal.setName(newAnimal.getName());
        }
       // if(Objects.nonNull(newAnimal.getType()) && !Objects.equals(newAnimal.getType(),dbAnimal.getType())){
         //   dbAnimal.setType(newAnimal.getType());
       // }
        if(Objects.nonNull(newAnimal.getPrice()) && !Objects.equals(newAnimal.getPrice(),dbAnimal.getPrice())){
            dbAnimal.setPrice(newAnimal.getPrice());
        }
        if(Objects.nonNull(newAnimal.getDescription()) && !Objects.equals(newAnimal.getDescription(),dbAnimal.getDescription())){
            dbAnimal.setDescription(newAnimal.getDescription());
        }
        if(Objects.nonNull(newAnimal.getAge()) && !Objects.equals(newAnimal.getAge(),dbAnimal.getAge())){
            dbAnimal.setAge(newAnimal.getAge());
        }
        return new ResponseEntity<>(animalRepository.save(dbAnimal),HttpStatus.OK);
    }

    public ResponseEntity<Animal> deleteAnimal(Long id){
        Optional<Animal> animal=animalRepository.findById(id);
        if(animal.isPresent()) {
            animalRepository.deleteById(id);
            ResponseEntity.ok(animal);
        }
        return ResponseEntity.notFound().build();
    }
//ajout d'un animal sans utlisation du patron grasp creator
    /*public ResponseEntity<Animal> addAnimalToFarmById(Long id, Animal newAnimal) {
        Animal event=farmRepository.findById(id).map(farm -> {
            newAnimal.setFarm(farm);
            return animalRepository.save(newAnimal);
        }).orElseThrow(()-> new ResourceNotFoundException("Not found farm id = "+id));

        return new ResponseEntity<>(event,HttpStatus.CREATED);
    }*/

  /*  public ResponseEntity<Animal> addAnimalToFarmById(Long id, Animal animalRequest) {
        Animal animal = farmRepository.findById(id).map(farm -> {
            // Utilisation du pattern Creator
            Animal newAnimal = farm.createAnimal(
                    animalRequest.getName(),
                    animalRequest.getType(),
                    animalRequest.getAge(),
                    animalRequest.getPrice(),
                    animalRequest.getDescription(),
                    animalRequest.getImages()
            );

            return animalRepository.save(newAnimal);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found farm id = " + id));

        return new ResponseEntity<>(animal, HttpStatus.CREATED);
    }*/
    //implimentation du grasp creator
  public ResponseEntity<Animal> addAnimalToFarmById(Long id, String animalType, Animal animalRequest) {
      AnimalFactory factory = animalFactories.get(animalType.toUpperCase());
      if (factory == null) {
          throw new IllegalArgumentException("Unknown animal type: " + animalType);
      }

      Animal animal = farmRepository.findById(id).map(farm -> {
          Animal newAnimal = farm.createAnimal(
                  factory,
                  animalRequest.getName(),
                  animalRequest.getAge(),
                  animalRequest.getPrice(),
                  animalRequest.getDescription(),
                  animalRequest.getImages()
          );

          return animalRepository.save(newAnimal);
      }).orElseThrow(() -> new ResourceNotFoundException("Not found farm id = " + id));

      return new ResponseEntity<>(animal, HttpStatus.CREATED);
  }
    public ResponseEntity<List<Animal>> getAllAnimalsByFarmId(Long farmId){
        if(!farmRepository.existsById(farmId)){
            throw new ResourceNotFoundException("Not found farm id : "+farmId);
        }

        return new ResponseEntity<>(animalRepository.findByFarmId(farmId), HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> deleteAllAnimalsFromFarmById(Long id){
        if(!farmRepository.existsById(id)){
            throw new ResourceNotFoundException("Not found");
        }

        animalRepository.deleteByFarmId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Animal> addAnimalToUserById(Long id, Animal newAnimal) {
        Animal animal=userRepository.findById(id).map(user -> {
            newAnimal.setUser(user);
            return animalRepository.save(newAnimal);
        }).orElseThrow(()-> new ResourceNotFoundException("Not found user id = "+id));

        return new ResponseEntity<>(animal,HttpStatus.CREATED);
    }

    public ResponseEntity<List<Animal>> getAllAnimalsByUserId(Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("Not found user id : "+userId);
        }

        return new ResponseEntity<>(animalRepository.findByUserId(userId), HttpStatus.OK);
    }

    public ResponseEntity<List<Animal>> deleteAllAnimalsFromUserById(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Not found");
        }

        animalRepository.deleteByUserId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
