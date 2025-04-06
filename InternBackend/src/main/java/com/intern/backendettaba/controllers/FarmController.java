package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.Event;
import com.intern.backendettaba.entities.Farm;
import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.services.FarmService;
import com.intern.backendettaba.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FarmController {
    private final FarmService farmService;

    private final ImageService imageService;


    @PostMapping(value = "/farm",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Farm> add(@RequestPart("farm") Farm farm,
                                    @RequestPart("imageFile") MultipartFile[] file){

        try {
            Set<Image> images=imageService.uploadImages(file);
            farm.setFarmImages(images);
            return farmService.saveFarm(farm);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/farm/{id}")
    public ResponseEntity<Farm> get(@PathVariable(name = "id") Long id){
        return farmService.getFarmByID(id);
    }

    @GetMapping("/farm")
    public ResponseEntity<List<Farm>> list(){
        return farmService.getAllFarms();
    }

    @PutMapping("/farm/{id}")
    public ResponseEntity<Farm> update(@PathVariable(name = "id") Long id,@RequestBody Farm farm){
        return farmService.updateFarm(farm,id);
    }

    @DeleteMapping("/farm/{id}")
    public ResponseEntity<Farm> delete(@PathVariable(name = "id") Long id){
        return farmService.deleteFarm(id);
    }
}
