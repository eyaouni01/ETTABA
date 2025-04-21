package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Chicken;
import com.intern.backendettaba.entities.Cow;
import com.intern.backendettaba.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnimalController {

    private final AnimalService animalService;

    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping("/animals")
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal) {
        return animalService.saveAnimal(animal);
    }

    @GetMapping("/animals/{id}")
    public ResponseEntity<Animal> getAnimal(@PathVariable("id") Long id) {
        return animalService.getAnimalByID(id);
    }

    @GetMapping("/animals")
    public ResponseEntity<List<Animal>> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @PutMapping("/animals/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("id") Long id, @RequestBody Animal animal) {
        return animalService.updateAnimal(animal, id);
    }

    @DeleteMapping("/animals/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable("id") Long id) {
        return animalService.deleteAnimal(id);
    }

    // Endpoint générique pour ajouter un animal à une ferme
    @PostMapping("/farms/{farmId}/animals/{animalType}")
    public ResponseEntity<Animal> addAnimalToFarm(
            @PathVariable("farmId") Long farmId,
            @PathVariable("animalType") String animalType,
            @RequestBody Animal animal) {
        return animalService.addAnimalToFarmById(farmId, animalType, animal);
    }

    // Endpoint spécifique pour ajouter une vache à une ferme
    @PostMapping("/farms/{farmId}/cows")
    public ResponseEntity<Cow> addCowToFarm(
            @PathVariable("farmId") Long farmId,
            @RequestBody Cow cow) {
        return animalService.addCowToFarm(farmId, cow);
    }

    // Endpoint spécifique pour ajouter un poulet à une ferme
    @PostMapping("/farms/{farmId}/chickens")
    public ResponseEntity<Chicken> addChickenToFarm(
            @PathVariable("farmId") Long farmId,
            @RequestBody Chicken chicken) {
        return animalService.addChickenToFarm(farmId, chicken);
    }

    @GetMapping("/farms/{farmId}/animals")
    public ResponseEntity<List<Animal>> getAllAnimalsByFarm(@PathVariable("farmId") Long farmId) {
        return animalService.getAllAnimalsByFarmId(farmId);
    }

    @DeleteMapping("/farms/{farmId}/animals")
    public ResponseEntity<List<Animal>> deleteAllAnimalsFromFarm(@PathVariable("farmId") Long farmId) {
        return animalService.deleteAllAnimalsFromFarmById(farmId);
    }

    // Endpoint pour ajouter un animal à un utilisateur
    @PostMapping("/users/{userId}/animals/{animalType}")
    public ResponseEntity<Animal> addAnimalToUser(
            @PathVariable("userId") Long userId,
            @PathVariable("animalType") String animalType,
            @RequestBody Animal animal) {
        return animalService.addAnimalToUserById(userId, animalType, animal);
    }

    @GetMapping("/users/{userId}/animals")
    public ResponseEntity<List<Animal>> getAllAnimalsByUser(@PathVariable("userId") Long userId) {
        return animalService.getAllAnimalsByUserId(userId);
    }

    @DeleteMapping("/users/{userId}/animals")
    public ResponseEntity<List<Animal>> deleteAllAnimalsFromUser(@PathVariable("userId") Long userId) {
        return animalService.deleteAllAnimalsFromUserById(userId);
    }
}