package com.intern.backendettaba.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.intern.backendettaba.enums.Etat;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ettaba {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate plantationDate;
    private LocalDate readyDate;
    @Column(nullable = false)
    private Float height;
    @Column(nullable = false)
    private Float width;
    private Double price;

    private LocalDate creationDate;
    private LocalDate boughtDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Farm farm;

    // Creator pattern method
    public Product createProduct(String name, Float boughtPrice, Float soldPrice, Float weight, String description, Etat etat, Set<Image> images) {
        Product product = new Product();
        product.setName(name);
        product.setBoughtPrice(boughtPrice);
        product.setSoldPrice(soldPrice);
        product.setWeight(weight);
        product.setDescription(description);
        product.setEtat(etat);
        product.setImages(images);
        product.setEttaba(this);
        product.setCreationDate(LocalDate.now());
        return product;
    }
}