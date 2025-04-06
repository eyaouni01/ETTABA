package com.intern.backendettaba.services;

import com.intern.backendettaba.entities.Event;
import com.intern.backendettaba.entities.Product;
import com.intern.backendettaba.repositories.EttabaRepository;
import com.intern.backendettaba.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final EttabaRepository ettabaRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(EttabaRepository ettabaRepository, ProductRepository productRepository) {
        this.ettabaRepository = ettabaRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<Product> saveProduct(Product product){

        //to be modified according to user action
        product.setBoughtDate(LocalDate.now());
        //
        product.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(productRepository.save(product),HttpStatus.CREATED);
    }

    public ResponseEntity<Product> getProductByID(Long id){
        /*
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            Product product1=product.get();
            product1.setProfit(Math.abs(product1.getBoughtPrice()- product1.getSoldPrice()));
            return ResponseEntity.ok(product);
        }
        else return ResponseEntity.notFound().build();

         */
        Product product=productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.setProfit(Math.abs(product.getBoughtPrice()- product.getSoldPrice()));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    public ResponseEntity<Product> updateProduct(Product newProduct,Long id){
        Product dbProduct=productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.valueOf(id)));
        if(Objects.nonNull(newProduct.getName()) && !Objects.equals(newProduct.getName(),dbProduct.getName())){
            dbProduct.setName(newProduct.getName());
        }
        if(Objects.nonNull(newProduct.getEtat()) && !Objects.equals(newProduct.getEtat(),dbProduct.getEtat())){
            dbProduct.setEtat(newProduct.getEtat());
        }
        if(Objects.nonNull(newProduct.getBoughtPrice()) && !Objects.equals(newProduct.getBoughtPrice(),dbProduct.getBoughtPrice())){
            dbProduct.setBoughtPrice(newProduct.getBoughtPrice());
        }
        if(Objects.nonNull(newProduct.getSoldPrice()) && !Objects.equals(newProduct.getSoldPrice(),dbProduct.getSoldPrice())){
            dbProduct.setSoldPrice(newProduct.getSoldPrice());
        }
        if(Objects.nonNull(newProduct.getWeight()) && !Objects.equals(newProduct.getWeight(),dbProduct.getWeight())){
            dbProduct.setWeight(newProduct.getWeight());
        }
        if(Objects.nonNull(newProduct.getDescription()) && !Objects.equals(newProduct.getDescription(),dbProduct.getDescription())){
            dbProduct.setDescription(newProduct.getDescription());
        }
        return new ResponseEntity<>(productRepository.save(dbProduct),HttpStatus.OK);
    }

    public ResponseEntity<Product> deleteProduct(Long id){
        Optional<Product> product=productRepository.findById(id);
        if(product.isPresent()) {
            productRepository.deleteById(id);
            ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }



    public ResponseEntity<Product> addProductToEttabaById(Long id, Product newProduct) {
        Product product=ettabaRepository.findById(id).map(ettaba -> {
            newProduct.setEttaba(ettaba);
            return productRepository.save(newProduct);
        }).orElseThrow(()-> new ResourceNotFoundException("Not found ettaba id = "+id));

        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    public ResponseEntity<List<Product>> getAllProductsByEttabaId(Long ettabaId){
        if(!ettabaRepository.existsById(ettabaId)){
            throw new ResourceNotFoundException("Not found ettaba id : "+ettabaId);
        }

        return new ResponseEntity<>(productRepository.findByEttabaId(ettabaId), HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> deleteAllProductsFromEttabaById(Long ettabaId){
        if(!ettabaRepository.existsById(ettabaId)){
            throw new ResourceNotFoundException("Not found");
        }

        productRepository.deleteByEttabaId(ettabaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
