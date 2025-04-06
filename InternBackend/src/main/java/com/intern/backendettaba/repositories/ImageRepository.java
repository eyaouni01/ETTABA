package com.intern.backendettaba.repositories;

import com.intern.backendettaba.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findById(Long id);
    Boolean existsImageById(Long id);
    void deleteImageById(Long id) ;
}
