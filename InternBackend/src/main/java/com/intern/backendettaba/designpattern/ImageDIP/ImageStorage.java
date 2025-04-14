package com.intern.backendettaba.designpattern.ImageDIP;

import com.intern.backendettaba.entities.Image;
import java.util.List;
import java.util.Optional;
//  principe DIP ici 👇
// interface qui définie qui définit les opérations de stockage des objets Image.

public interface ImageStorage {
    Image save(Image image);

    void deleteById(Long id);

    List<Image> findAll();

    Optional<Image> findById(Long id);

    boolean existsById(Long id);
}
