package com.intern.backendettaba.repositories;

import com.intern.backendettaba.entities.Ettaba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EttabaRepository extends JpaRepository<Ettaba,Long> {

    List<Ettaba> findByFarmId(Long farmId);
    List<Ettaba> findByUserId(Long farmId);

    @Transactional
    void deleteByFarmId(Long farmId);

    @Transactional
    void deleteByUserId(Long farmId);
}
