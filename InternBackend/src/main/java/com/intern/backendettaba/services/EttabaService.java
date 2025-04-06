package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Ettaba;
import com.intern.backendettaba.entities.Farm;
import com.intern.backendettaba.repositories.EttabaRepository;
import com.intern.backendettaba.repositories.FarmRepository;
import com.intern.backendettaba.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EttabaService {

    private final UserRepository userRepository;
    private final FarmRepository farmRepository;
    private final EttabaRepository ettabaRepository;

    public ResponseEntity<Ettaba> saveEttaba(@RequestBody Ettaba ettaba){

        //to be modified according to user action
        ettaba.setBoughtDate(LocalDate.now());
        //
        ettaba.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(ettabaRepository.save(ettaba),HttpStatus.CREATED);
    }



    public ResponseEntity<List<Ettaba>> getAllEttabas(){
        return ResponseEntity.ok(ettabaRepository.findAll());
    }


    public ResponseEntity<Ettaba> getEttabaById(Long id){
        /*
        Optional<Ettaba> ettaba = ettabaRepository.findById(id);
        if (ettaba.isPresent()) return ResponseEntity.ok(ettaba);
        else return ResponseEntity.notFound().build();
        */

        Ettaba ettaba = ettabaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ResponseEntity<>(ettaba,HttpStatus.OK);

    }

    public ResponseEntity<Ettaba> updateEttaba(Long id, Ettaba updatedEttaba){
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Ettaba existingEttaba = ettabaRepository.findById(id).orElseThrow(()->new EntityNotFoundException(String.valueOf(id)));
        if(Objects.nonNull(updatedEttaba.getHeight())&& !Objects.equals(existingEttaba.getHeight(),updatedEttaba.getHeight())){
            existingEttaba.setHeight(updatedEttaba.getHeight());
        }
        if(Objects.nonNull(updatedEttaba.getWidth())&& !Objects.equals(existingEttaba.getWidth(),updatedEttaba.getWidth())){
            existingEttaba.setWidth(updatedEttaba.getWidth());
        }
        if(Objects.nonNull(updatedEttaba.getPrice())&& !Objects.equals(existingEttaba.getPrice(),updatedEttaba.getPrice())){
            existingEttaba.setPrice(updatedEttaba.getPrice());
        }
        if(Objects.nonNull(updatedEttaba.getReadyDate())&& !Objects.equals(existingEttaba.getReadyDate(),updatedEttaba.getReadyDate())){
            existingEttaba.setReadyDate(updatedEttaba.getReadyDate());
        }
        if(Objects.nonNull(updatedEttaba.getPlantationDate())&& !Objects.equals(existingEttaba.getPlantationDate(),updatedEttaba.getPlantationDate())){
            existingEttaba.setPlantationDate(updatedEttaba.getPlantationDate());
        }

        return ResponseEntity.ok(ettabaRepository.save(existingEttaba));
    }

    public ResponseEntity<Ettaba> deleteEttaba(Long id){
        Optional<Ettaba> existingEttaba = ettabaRepository.findById(id);
        if(existingEttaba.isPresent()) {
            ettabaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Ettaba> addEttabaToFarmById(Long id,Ettaba newEttaba) {
        Ettaba ettaba=farmRepository.findById(id).map(farm -> {
            newEttaba.setFarm(farm);
            return ettabaRepository.save(newEttaba);
        }).orElseThrow(()-> new ResourceNotFoundException("Not found farm id = "+id));

        return new ResponseEntity<>(ettaba,HttpStatus.CREATED);
    }

    public ResponseEntity<List<Ettaba>> getAllEttabasByFarmId(Long farmId){
        if(!farmRepository.existsById(farmId)){
            throw new ResourceNotFoundException("Not found farm id : "+farmId);
        }

        return new ResponseEntity<>(ettabaRepository.findByFarmId(farmId), HttpStatus.OK);
    }

    public ResponseEntity<List<Ettaba>> deleteAllEttabasFromFarmById(Long id){
        if(!farmRepository.existsById(id)){
            throw new ResourceNotFoundException("Not found");
        }

        ettabaRepository.deleteByFarmId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<Ettaba> addEttabaToUserById(Long id,Ettaba newEttaba) {
        Ettaba ettaba=userRepository.findById(id).map(user -> {
            newEttaba.setUser(user);
            return ettabaRepository.save(newEttaba);
        }).orElseThrow(()-> new ResourceNotFoundException("Not found user id = "+id));

        return new ResponseEntity<>(ettaba,HttpStatus.CREATED);
    }

    public ResponseEntity<List<Ettaba>> getAllEttabasByUserId(Long userId){
        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("Not found user id : "+userId);
        }

        return new ResponseEntity<>(ettabaRepository.findByUserId(userId), HttpStatus.OK);
    }

    public ResponseEntity<List<Ettaba>> deleteAllEttabasFromUserById(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Not found");
        }

        ettabaRepository.deleteByUserId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
