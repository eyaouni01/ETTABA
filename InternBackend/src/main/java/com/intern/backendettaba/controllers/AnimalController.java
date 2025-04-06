package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.*;
import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.services.AnimalService;
import com.intern.backendettaba.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService animalService;

    private final ImageService imageService;

    @PostMapping(value = "/animal",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Animal> add(@RequestPart("animal") Animal animal,
                                       @RequestPart("imageFile") MultipartFile[] file){
        try {
            Set<Image> images=imageService.uploadImages(file);
            animal.setAnimalImages(images);
            return animalService.saveAnimal(animal);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/animal/{id}")
    public ResponseEntity<Animal> get(@PathVariable(name = "id") Long id){
        return animalService.getAnimalByID(id);
    }

    @GetMapping("/animal")
    public ResponseEntity<List<Animal>> list(){
        return animalService.getAllAnimals();
    }

    @PutMapping("/animal/{id}")
    public ResponseEntity<Animal> update(@PathVariable(name = "id") Long id,@RequestBody Animal animal){
        return animalService.updateAnimal(animal,id);
    }

    @DeleteMapping("/animal/{id}")
    public ResponseEntity<Animal> delete(@PathVariable(name = "id") Long id){
        return animalService.deleteAnimal(id);
    }

    @GetMapping("/farm/{id}/animal")
    public ResponseEntity<List<Animal>> listByFarm(@PathVariable(name = "id") Long id){
        return animalService.getAllAnimalsByFarmId(id);
    }
    @PostMapping("/farm/{id}/animal")
    public ResponseEntity<Animal> addToFarm(@PathVariable(name = "id") Long id,
                                            @RequestPart("animal") Animal animal,
                                            @RequestPart("imageFile") MultipartFile[] file){

        try {
            Set<Image> images=imageService.uploadImages(file);
            animal.setAnimalImages(images);
            return animalService.addAnimalToFarmById(id,animal);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @DeleteMapping("/farm/{id}/animal")
    public ResponseEntity<List<Animal>> deleteAllFromFarm(@PathVariable(name = "id") Long id){
        return animalService.deleteAllAnimalsFromFarmById(id);
    }

    @GetMapping("/user/{id}/animal")
    public ResponseEntity<List<Animal>> listByUser(@PathVariable(name = "id") Long id){
        return animalService.getAllAnimalsByUserId(id);
    }
    @PostMapping("/user/{id}/animal")
    public ResponseEntity<Animal> addToUser(@PathVariable(name = "id") Long id,
                                            @RequestBody Animal animal){
        return animalService.addAnimalToUserById(id,animal);
    }
    @DeleteMapping("/user/{id}/animal")
    public ResponseEntity<List<Animal>> deleteAllFromUser(@PathVariable(name = "id") Long id){
        return animalService.deleteAllAnimalsFromUserById(id);
    }
}
