package com.intern.backendettaba.entities;

import com.intern.backendettaba.enums.Etat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private Etat etat;
    private Float boughtPrice;
    private Float soldPrice;
    @Transient
    private Float profit;
    private Float weight;
    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate creationDate;
    private LocalDate boughtDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_images",
            joinColumns = {@JoinColumn(name = "product_id")},inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private Set<Image> productImages;


    @ManyToOne
    //Adding the name
    @JoinColumn(name = "ettaba_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Ettaba ettaba;

}
