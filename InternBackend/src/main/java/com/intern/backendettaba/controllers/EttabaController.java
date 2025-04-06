package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.Ettaba;
import com.intern.backendettaba.services.EttabaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EttabaController {
    private final EttabaService ettabaService;

    @Autowired
    public EttabaController(EttabaService ettabaService) {
        this.ettabaService = ettabaService;
    }

    @PostMapping("/ettaba")
    public ResponseEntity<Ettaba> add(@RequestBody Ettaba ettaba){
        return ettabaService.saveEttaba(ettaba);
    }


    @GetMapping("/ettaba")
    public ResponseEntity<List<Ettaba>> list(){
        return ettabaService.getAllEttabas();
    }


    @GetMapping("/ettaba/{id}")
    public ResponseEntity<Ettaba> get(@PathVariable(name = "id") Long id){
        return ettabaService.getEttabaById(id);
    }

    @DeleteMapping("/ettaba/{id}")
    public ResponseEntity<Ettaba> delete(@PathVariable(name = "id") Long id){
        return ettabaService.deleteEttaba(id);
    }


    @PutMapping("/ettaba/{id}")
    public ResponseEntity<Ettaba> update(@PathVariable(name = "id") Long id, @RequestBody Ettaba updatedEttaba){
        return ettabaService.updateEttaba(id,updatedEttaba);
    }

    @GetMapping("/farm/{id}/ettaba")
    public ResponseEntity<List<Ettaba>> listByFarm(@PathVariable(name = "id") Long id){
        return ettabaService.getAllEttabasByFarmId(id);
    }
    @PostMapping("/farm/{id}/ettaba")
    public ResponseEntity<Ettaba> addToFarm(@PathVariable(name = "id") Long id,
                                            @RequestBody Ettaba ettaba){
        return ettabaService.addEttabaToFarmById(id,ettaba);
    }
    @DeleteMapping("/farm/{id}/ettaba")
    public ResponseEntity<List<Ettaba>> deleteAllFromFarm(@PathVariable(name = "id") Long id){
        return ettabaService.deleteAllEttabasFromFarmById(id);
    }


    @GetMapping("/user/{id}/ettaba")
    public ResponseEntity<List<Ettaba>> listByUser(@PathVariable(name = "id") Long id){
        return ettabaService.getAllEttabasByUserId(id);
    }
    @PostMapping("/user/{id}/ettaba")
    public ResponseEntity<Ettaba> addToUser(@PathVariable(name = "id") Long id,
                                            @RequestBody Ettaba ettaba){
        return ettabaService.addEttabaToUserById(id,ettaba);
    }
    @DeleteMapping("/user/{id}/ettaba")
    public ResponseEntity<List<Ettaba>> deleteAllFromUser(@PathVariable(name = "id") Long id){
        return ettabaService.deleteAllEttabasFromUserById(id);
    }
}
