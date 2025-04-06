package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Farm;
import com.intern.backendettaba.repositories.FarmRepository;
import com.intern.backendettaba.repositories.FarmRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FarmService {
    private final FarmRepository farmRepository;

    @Autowired
    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public ResponseEntity<Farm> saveFarm(Farm farm){

        farm.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(farmRepository.save(farm),HttpStatus.CREATED);
    }

    public ResponseEntity<Farm> getFarmByID(Long id){
        /*
        Optional<Farm> farm = farmRepository.findById(id);
        if(farm.isPresent()) {
            Farm farm1=farm.get();
            farm1.setNumberSoldEttabas(farm1.getNumberEttabas()-farm1.getNumberAvailableEttabas());
            return ResponseEntity.ok(farm);
        }
        else return ResponseEntity.notFound().build();

         */

        Farm farm=farmRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        //if(farm.getNumberEttabas()!=null && farm.getNumberAvailableEttabas()!=null) farm.setNumberSoldEttabas(farm.getNumberEttabas()-farm.getNumberAvailableEttabas());

        return new ResponseEntity<>(farm, HttpStatus.OK);
    }

    public ResponseEntity<List<Farm>> getAllFarms(){
        return ResponseEntity.ok(farmRepository.findAll());
    }

    public ResponseEntity<Farm> updateFarm(Farm newFarm,Long id){
        Farm dbFarm=farmRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.valueOf(id)));
        if(Objects.nonNull(newFarm.getName()) && !Objects.equals(newFarm.getName(),dbFarm.getName())){
            dbFarm.setName(newFarm.getName());
        }
        if(Objects.nonNull(newFarm.getLatitude()) && !Objects.equals(newFarm.getLatitude(),dbFarm.getLatitude())){
            dbFarm.setLatitude(newFarm.getLatitude());
        }
        if(Objects.nonNull(newFarm.getLongitude()) && !Objects.equals(newFarm.getLongitude(),dbFarm.getLongitude())){
            dbFarm.setLongitude(newFarm.getLongitude());
        }
        if(Objects.nonNull(newFarm.getDescription()) && !Objects.equals(newFarm.getDescription(),dbFarm.getDescription())){
            dbFarm.setDescription(newFarm.getDescription());
        }

        return ResponseEntity.ok(farmRepository.save(dbFarm));
    }

    public ResponseEntity<Farm> deleteFarm(Long id){
        Optional<Farm> farm=farmRepository.findById(id);
        if(farm.isPresent()) {
            farmRepository.deleteById(id);
            ResponseEntity.ok(farm);
        }
        return ResponseEntity.notFound().build();
    }
}
