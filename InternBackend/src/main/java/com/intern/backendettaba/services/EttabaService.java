package com.intern.backendettaba.services;

import com.intern.backendettaba.designpattern.revenuestrategy.EttabaRevenue;
import com.intern.backendettaba.designpattern.revenuestrategy.RevenueContext;
import com.intern.backendettaba.entities.Ettaba;
import com.intern.backendettaba.entities.Farm;
import com.intern.backendettaba.interfaces.RevenueStrategy;
import com.intern.backendettaba.repositories.EttabaRepository;
import com.intern.backendettaba.repositories.FarmRepository;
import com.intern.backendettaba.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
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

    /**
     * Valide les données d'un Ettaba
     * @param ettaba l'ettaba à valider
     * @throws ValidationException si les données ne sont pas valides
     */
    private void validateEttaba(Ettaba ettaba) {
        // Validation de la hauteur
        if (ettaba.getHeight() != null && ettaba.getHeight() <= 0) {
            throw new ValidationException("La hauteur doit être strictement positive");
        }
        
        // Validation de la largeur
        if (ettaba.getWidth() != null && ettaba.getWidth() <= 0) {
            throw new ValidationException("La largeur doit être strictement positive");
        }
        
        // Validation du prix
        if (ettaba.getPrice() != null && ettaba.getPrice() <= 0) {
            throw new ValidationException("Le prix doit être strictement positif");
        }
        
        // Validation des dates
        if (ettaba.getPlantationDate() != null && ettaba.getReadyDate() != null 
                && !ettaba.getReadyDate().isAfter(ettaba.getPlantationDate())) {
            throw new ValidationException("La date de maturité doit être postérieure à la date de plantation");
        }
    }

    public ResponseEntity<Ettaba> saveEttaba(@RequestBody Ettaba ettaba) {
        // Validation manuelle des données
        validateEttaba(ettaba);
        
        // Initialisation des dates
        ettaba.setBoughtDate(LocalDate.now());
        ettaba.setCreationDate(LocalDate.now());
        
        return new ResponseEntity<>(ettabaRepository.save(ettaba), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Ettaba>> getAllEttabas() {
        return ResponseEntity.ok(ettabaRepository.findAll());
    }

    public ResponseEntity<Ettaba> getEttabaById(Long id) {
        Ettaba ettaba = ettabaRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        RevenueStrategy strategy = new EttabaRevenue(ettaba);
        RevenueContext context = new RevenueContext(strategy);
        float revenu = context.calculer();

        return new ResponseEntity<>(ettaba, HttpStatus.OK);
    }

    public ResponseEntity<Float> getEttabaRevenues() {
        float total = 0f;
        List<Ettaba> ettaba = ettabaRepository.findAll();
        for (Ettaba ettaba1 : ettaba) {
            RevenueStrategy strategy = new EttabaRevenue(ettaba1);
            RevenueContext context = new RevenueContext(strategy);
            float revenu = context.calculer();
            total += revenu;
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    public ResponseEntity<Ettaba> updateEttaba(Long id, Ettaba updatedEttaba) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        Ettaba existingEttaba = ettabaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

        // Création d'une entité temporaire pour validation
        Ettaba tempEttaba = new Ettaba();
        tempEttaba.setHeight(updatedEttaba.getHeight() != null ? updatedEttaba.getHeight() : existingEttaba.getHeight());
        tempEttaba.setWidth(updatedEttaba.getWidth() != null ? updatedEttaba.getWidth() : existingEttaba.getWidth());
        tempEttaba.setPrice(updatedEttaba.getPrice() != null ? updatedEttaba.getPrice() : existingEttaba.getPrice());
        tempEttaba.setPlantationDate(updatedEttaba.getPlantationDate() != null ? 
                updatedEttaba.getPlantationDate() : existingEttaba.getPlantationDate());
        tempEttaba.setReadyDate(updatedEttaba.getReadyDate() != null ? 
                updatedEttaba.getReadyDate() : existingEttaba.getReadyDate());
        
        // Validation des données
        validateEttaba(tempEttaba);
        
        // Mise à jour des champs
        if (Objects.nonNull(updatedEttaba.getHeight())) {
            existingEttaba.setHeight(updatedEttaba.getHeight());
        }
        if (Objects.nonNull(updatedEttaba.getWidth())) {
            existingEttaba.setWidth(updatedEttaba.getWidth());
        }
        if (Objects.nonNull(updatedEttaba.getPrice())) {
            existingEttaba.setPrice(updatedEttaba.getPrice());
        }
        if (Objects.nonNull(updatedEttaba.getReadyDate())) {
            existingEttaba.setReadyDate(updatedEttaba.getReadyDate());
        }
        if (Objects.nonNull(updatedEttaba.getPlantationDate())) {
            existingEttaba.setPlantationDate(updatedEttaba.getPlantationDate());
        }

        return ResponseEntity.ok(ettabaRepository.save(existingEttaba));
    }

    public ResponseEntity<Ettaba> deleteEttaba(Long id) {
        Optional<Ettaba> existingEttaba = ettabaRepository.findById(id);
        if (existingEttaba.isPresent()) {
            ettabaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Ettaba> addEttabaToFarmById(Long id, Ettaba ettabaRequest) {
        // Validation des données avant de créer l'ettaba
        validateEttaba(ettabaRequest);
        
        Ettaba ettaba = farmRepository.findById(id).map(farm -> {
            // Utilisation du pattern Creator
            Ettaba newEttaba = farm.createEttaba(
                    ettabaRequest.getPlantationDate(),
                    ettabaRequest.getReadyDate(),
                    ettabaRequest.getHeight(),
                    ettabaRequest.getWidth(),
                    ettabaRequest.getPrice()
            );
            return ettabaRepository.save(newEttaba);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found farm id = " + id));

        return new ResponseEntity<>(ettaba, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Ettaba>> getAllEttabasByFarmId(Long farmId) {
        if (!farmRepository.existsById(farmId)) {
            throw new ResourceNotFoundException("Not found farm id : " + farmId);
        }

        return new ResponseEntity<>(ettabaRepository.findByFarmId(farmId), HttpStatus.OK);
    }

    public ResponseEntity<List<Ettaba>> deleteAllEttabasFromFarmById(Long id) {
        if (!farmRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found");
        }

        ettabaRepository.deleteByFarmId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Ettaba> addEttabaToUserById(Long id, Ettaba newEttaba) {
        // Validation des données avant de créer l'ettaba
        validateEttaba(newEttaba);
        
        Ettaba ettaba = userRepository.findById(id).map(user -> {
            newEttaba.setUser(user);
            return ettabaRepository.save(newEttaba);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found user id = " + id));

        return new ResponseEntity<>(ettaba, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Ettaba>> getAllEttabasByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Not found user id : " + userId);
        }

        return new ResponseEntity<>(ettabaRepository.findByUserId(userId), HttpStatus.OK);
    }

    public ResponseEntity<List<Ettaba>> deleteAllEttabasFromUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found");
        }

        ettabaRepository.deleteByUserId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}