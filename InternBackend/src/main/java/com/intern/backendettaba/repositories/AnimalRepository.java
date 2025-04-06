package com.intern.backendettaba.repositories;

import com.intern.backendettaba.entities.Animal;
import com.intern.backendettaba.entities.Ettaba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal,Long> {
    List<Animal> findByFarmId(Long farmId);
    List<Animal> findByUserId(Long farmId);

    @Transactional
    void deleteByFarmId(Long farmId);

    @Transactional
    void deleteByUserId(Long farmId);
}
