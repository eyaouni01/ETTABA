package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Address;
import com.intern.backendettaba.repositories.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<Address> saveAddress(Address address){
        return new ResponseEntity<>(addressRepository.save(address), HttpStatus.CREATED);
    }

    public ResponseEntity<Address> getAddressByID(Long id){
        /*
        Optional<Address> address = addressRepository.findById(id);
        if(address.isPresent()) {
            Address address1=address.get();
            address1.setProfit(Math.abs(address1.getBoughtPrice()- address1.getSoldPrice()));
            return ResponseEntity.ok(address);
        }
        else return ResponseEntity.notFound().build();
        
         */
        Address address=addressRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    public ResponseEntity<List<Address>> getAllAddresss(){
        return ResponseEntity.ok(addressRepository.findAll());
    }

    public ResponseEntity<Address> updateAddress(Address newAddress,Long id){
        Address dbAddress=addressRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.valueOf(id)));
        if(Objects.nonNull(newAddress.getCity()) && !Objects.equals(newAddress.getCity(),dbAddress.getCity())){
            dbAddress.setCity(newAddress.getCity());
        }
        if(Objects.nonNull(newAddress.getHouseNumber()) && !Objects.equals(newAddress.getHouseNumber(),dbAddress.getHouseNumber())){
            dbAddress.setHouseNumber(newAddress.getHouseNumber());
        }
        if(Objects.nonNull(newAddress.getRegion()) && !Objects.equals(newAddress.getRegion(),dbAddress.getRegion())){
            dbAddress.setRegion(newAddress.getRegion());
        }
        if(Objects.nonNull(newAddress.getState()) && !Objects.equals(newAddress.getState(),dbAddress.getState())){
            dbAddress.setState(newAddress.getState());
        }
        if(Objects.nonNull(newAddress.getPostalCode()) && !Objects.equals(newAddress.getPostalCode(),dbAddress.getPostalCode())){
            dbAddress.setPostalCode(newAddress.getPostalCode());
        }
        if(Objects.nonNull(newAddress.getStreet()) && !Objects.equals(newAddress.getStreet(),dbAddress.getStreet())){
            dbAddress.setStreet(newAddress.getStreet());
        }
        return new ResponseEntity<>(addressRepository.save(dbAddress),HttpStatus.OK);
    }

    public ResponseEntity<Address> deleteAddress(Long id){
        Optional<Address> address=addressRepository.findById(id);
        if(address.isPresent()) {
            addressRepository.deleteById(id);
            ResponseEntity.ok(address);
        }
        return ResponseEntity.notFound().build();
    }
}
