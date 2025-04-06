package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.Address;
import com.intern.backendettaba.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/address")
    public ResponseEntity<Address> add(@RequestBody Address address){
        return addressService.saveAddress(address);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<Address> get(@PathVariable(name = "id") Long id){
        return addressService.getAddressByID(id);
    }

    @GetMapping("/address")
    public ResponseEntity<List<Address>> list(){
        return addressService.getAllAddresss();
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<Address> update(@PathVariable(name = "id") Long id,@RequestBody Address address){
        return addressService.updateAddress(address,id);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Address> delete(@PathVariable(name = "id") Long id){
        return addressService.deleteAddress(id);
    }
}
