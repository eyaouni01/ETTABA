package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Ettaba;
import com.intern.backendettaba.repositories.AnimalRepository;
import com.intern.backendettaba.repositories.FarmRepository;
import com.intern.backendettaba.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final AnimalRepository animalRepository;

    public ResponseEntity<Animal> saveAnimal(Animal animal){

        //to be modified according to user action
        animal.setBoughtDate(LocalDate.now());
        //
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
        if(Objects.nonNull(newAnimal.getType()) && !Objects.equals(newAnimal.getType(),dbAnimal.getType())){
            dbAnimal.setType(newAnimal.getType());
        }
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

    public ResponseEntity<Animal> addAnimalToFarmById(Long id, Animal newAnimal) {
        Animal event=farmRepository.findById(id).map(farm -> {
            newAnimal.setFarm(farm);
            return animalRepository.save(newAnimal);
        }).orElseThrow(()-> new ResourceNotFoundException("Not found farm id = "+id));

        return new ResponseEntity<>(event,HttpStatus.CREATED);
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
