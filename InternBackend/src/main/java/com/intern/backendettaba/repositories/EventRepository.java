package com.intern.backendettaba.repositories;

import com.intern.backendettaba.entities.Ettaba;
import com.intern.backendettaba.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByFarmId(Long farmId);

    @Transactional
    void deleteByFarmId(Long farmId);
}
