package com.intern.backendettaba.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.intern.backendettaba.enums.Etat;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.AssertTrue;

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
    @Positive(message = "La hauteur doit être positive")
    private Float height;

    @Column(nullable = false)
    @Positive(message = "La largeur doit être positive")
    private Float width;

    @Positive(message = "Le prix doit être positif")
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

    // Méthode de validation pour la contrainte: readyDateAfterPlantation
    @AssertTrue(message = "La date de récolte doit être postérieure à la date de plantation")
    private boolean isReadyDateAfterPlantation() {
        if (plantationDate == null || readyDate == null) {
            return true; // Ne pas valider si les dates ne sont pas définies
        }
        return readyDate.isAfter(plantationDate);
    }

    // Creator pattern method
    public Product createProduct(String name, Float boughtPrice, Float soldPrice, Float weight,
                                 String description, Etat etat, Set<Image> images) {
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