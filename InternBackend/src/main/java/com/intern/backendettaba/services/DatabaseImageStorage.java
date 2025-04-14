package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.designpattern.ImageDIP.ImageStorage;
import com.intern.backendettaba.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//  principe DIP ici 👇
// Cette classe implémente l'interface ImageStorage en utilisant ImageRepository 
// pour réaliser les opérations de persistance.

@Service
@RequiredArgsConstructor
public class DatabaseImageStorage implements ImageStorage {
    private final ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.deleteImageById(id);
    }

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return imageRepository.existsImageById(id);
    }
}
