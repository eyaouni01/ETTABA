package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.Event;
import com.intern.backendettaba.entities.Farm;
import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.entities.Product;
import com.intern.backendettaba.services.ImageService;
import com.intern.backendettaba.services.ProductService;
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
public class ProductController {

    private final ProductService productService;

    private final ImageService imageService;

    @PostMapping(value = "/product",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> add(@RequestPart("product") Product product,
                                       @RequestPart("imageFile") MultipartFile[] file){
        try {
            Set<Image> images=imageService.uploadImages(file);
            product.setProductImages(images);
            return productService.saveProduct(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> get(@PathVariable(name = "id") Long id){
        return productService.getProductByID(id);
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> list(){
        return productService.getAllProducts();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> update(@PathVariable(name = "id") Long id,@RequestBody Product product){
        return productService.updateProduct(product,id);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> delete(@PathVariable(name = "id") Long id){
        return productService.deleteProduct(id);
    }

    @GetMapping("/ettaba/{id}/product")
    public ResponseEntity<List<Product>> listByEttaba(@PathVariable(name = "id") Long id){
        return productService.getAllProductsByEttabaId(id);
    }
    @PostMapping(value = "/ettaba/{id}/product",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> addToEttaba(@PathVariable(name = "id") Long id,
                                               @RequestPart("product") Product product,
                                               @RequestPart("imageFile") MultipartFile[] file){

        try {
            Set<Image> images=imageService.uploadImages(file);
            product.setProductImages(images);
            return productService.addProductToEttabaById(id,product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @DeleteMapping("/ettaba/{id}/product")
    public ResponseEntity<List<Product>> deleteAllFromEttaba(@PathVariable(name = "id") Long id){
        return productService.deleteAllProductsFromEttabaById(id);
    }
}
