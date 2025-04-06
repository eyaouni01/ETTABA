package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;


    public Set<Image> uploadImages(MultipartFile[] multipartFiles) throws IOException {
        Set<Image> images=new HashSet<>();
        for (MultipartFile file:multipartFiles){
            Image image = new Image(null,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            images.add(image);
        }
        return images;
    }

    public Image uploadImage(MultipartFile multipartFile) throws IOException {
        return new Image(null,
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes()
        );
    }

    public void deleteImageById(Long id) {
        imageRepository.deleteImageById(id);
    }

    public List<Image> findAll() {return imageRepository.findAll(); }

    public Image addImage(Image image) { return imageRepository.save(image); }

    public Optional<Image> getById(Long id) { return imageRepository.findById(id); }

}
