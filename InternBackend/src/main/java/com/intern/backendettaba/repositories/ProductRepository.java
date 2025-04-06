package com.intern.backendettaba.repositories;

import com.intern.backendettaba.entities.Event;
import com.intern.backendettaba.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByEttabaId(Long ettabaId);

    @Transactional
    void deleteByEttabaId(Long ettabaId);
}
