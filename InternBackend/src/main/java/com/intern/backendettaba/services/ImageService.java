package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.designpattern.ImageDIP.ImageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
//  principe DIP ici 👇
// Ce service dépend de l'interface ImageStorage pour interagir avec la couche de persistance des images.
// le service dépendait avant directement du repository ImageRepository
// maintenant il dépend de l'interface ImageStorage,

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageStorage imageStorage;

    public Set<Image> uploadImages(MultipartFile[] multipartFiles) throws IOException {
        Set<Image> images = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            Image image = new Image(null,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes());
            images.add(imageStorage.save(image));
        }
        return images;
    }

    public Image uploadImage(MultipartFile multipartFile) throws IOException {
        Image image = new Image(null,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes());
        return imageStorage.save(image);
    }

    public void deleteImageById(Long id) {
        imageStorage.deleteById(id);
    }

    public List<Image> findAll() {
        return imageStorage.findAll();
    }

    public Image addImage(Image image) {
        return imageStorage.save(image);
    }

    public Optional<Image> getById(Long id) {
        return imageStorage.findById(id);
    }
}