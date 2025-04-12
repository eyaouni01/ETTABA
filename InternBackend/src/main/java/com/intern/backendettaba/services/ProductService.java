package com.intern.backendettaba.services;

import com.intern.backendettaba.designpattern.revenuestrategy.EttabaRevenue;
import com.intern.backendettaba.designpattern.revenuestrategy.ProductRevenue;
import com.intern.backendettaba.designpattern.revenuestrategy.RevenueContext;
import com.intern.backendettaba.entities.Ettaba;
import com.intern.backendettaba.entities.Event;
import com.intern.backendettaba.entities.Product;
import com.intern.backendettaba.interfaces.RevenueStrategy;
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

    public ResponseEntity<Product> saveProduct(Product product) {
        // to be modified according to user action
        product.setBoughtDate(LocalDate.now());
        //
        product.setCreationDate(LocalDate.now());
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
    }

    public ResponseEntity<Product> getProductByID(Long id) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.setProfit(Math.abs(product.getBoughtPrice() - product.getSoldPrice()));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<Float> getProductRevenues() {
        float total = 0f;
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            RevenueStrategy strategy = new ProductRevenue(product);
            RevenueContext context = new RevenueContext(strategy);
            float revenu = context.calculer();
            total += revenu;
        }

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    public ResponseEntity<Product> updateProduct(Product newProduct, Long id) {
        Product dbProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        // State Pattern ici 👇
        // si canUpdate() retourne true on peut modifier sinon rien ne change dans le
        // produit
        /*if (!dbProduct.getCurrentState().canUpdate()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }*/
        // 👉mise en commentaire pour appliquer
        // Grasp Pattern ici 👇 (Low Coupling)
        // appel à la méthode ajoutée à
        //Product qui encapsule la méme logique appliquée
        //dans State Patern(pour l'update)
        if (!dbProduct.canBeUpdated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        if (Objects.nonNull(newProduct.getName()) && !Objects.equals(newProduct.getName(), dbProduct.getName())) {
            dbProduct.setName(newProduct.getName());
        }
        if (Objects.nonNull(newProduct.getEtat()) && !Objects.equals(newProduct.getEtat(), dbProduct.getEtat())) {
            dbProduct.setEtat(newProduct.getEtat());
        }
        if (Objects.nonNull(newProduct.getBoughtPrice())
                && !Objects.equals(newProduct.getBoughtPrice(), dbProduct.getBoughtPrice())) {
            dbProduct.setBoughtPrice(newProduct.getBoughtPrice());
        }
        if (Objects.nonNull(newProduct.getSoldPrice())
                && !Objects.equals(newProduct.getSoldPrice(), dbProduct.getSoldPrice())) {
            dbProduct.setSoldPrice(newProduct.getSoldPrice());
        }
        if (Objects.nonNull(newProduct.getWeight()) && !Objects.equals(newProduct.getWeight(), dbProduct.getWeight())) {
            dbProduct.setWeight(newProduct.getWeight());
        }
        if (Objects.nonNull(newProduct.getDescription())
                && !Objects.equals(newProduct.getDescription(), dbProduct.getDescription())) {
            dbProduct.setDescription(newProduct.getDescription());
        }

        return new ResponseEntity<>(productRepository.save(dbProduct), HttpStatus.OK);
    }

    public ResponseEntity<Product> deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + id));

        // State Pattern ici 👇
        // si canDelete() retourne true (çà veux dire que le produit est SEED ou
        // INPROGRESS) donc il sera supprimé
        // sinon (produit READY) il ne sera pas supprimé
        /*if (!product.getCurrentState().canDelete()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }*/
        // 👉mise en commentaire pour appliquer
        // Grasp Pattern ici 👇 (Low Coupling)
        // appel à la méthode ajoutée à
        // Product qui encapsule la méme logique appliquée
        // dans State Patern(pour la suppression)
        if (!product.canBeDeleted()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<Product> addProductToEttabaById(Long id, Product productRequest) {
        Product product = ettabaRepository.findById(id).map(ettaba -> {
            // Utilisation du pattern Creator
            Product newProduct = ettaba.createProduct(
                    productRequest.getName(),
                    productRequest.getBoughtPrice(),
                    productRequest.getSoldPrice(),
                    productRequest.getWeight(),
                    productRequest.getDescription(),
                    productRequest.getEtat(),
                    productRequest.getImages());

            return productRepository.save(newProduct);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found ettaba id = " + id));

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Product>> getAllProductsByEttabaId(Long ettabaId) {
        if (!ettabaRepository.existsById(ettabaId)) {
            throw new ResourceNotFoundException("Not found ettaba id : " + ettabaId);
        }

        return new ResponseEntity<>(productRepository.findByEttabaId(ettabaId), HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> deleteAllProductsFromEttabaById(Long ettabaId) {
        if (!ettabaRepository.existsById(ettabaId)) {
            throw new ResourceNotFoundException("Not found");
        }

        productRepository.deleteByEttabaId(ettabaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
