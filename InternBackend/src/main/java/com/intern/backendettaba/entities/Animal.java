package com.intern.backendettaba.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "animal_type")
public abstract class Animal {
    @Id
    @SequenceGenerator(
            name = "animal_sequence",
            sequenceName = "animal_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "animal_sequence"
    )
    @Column(updatable = false)
    private Long id;

    private String name;

    @Column(
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            nullable = false
    )
    private Integer age;

    @Column(nullable = false)
    private Float price;

    private LocalDate creationDate;
    private LocalDate boughtDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "animal_images",
            joinColumns = {@JoinColumn(name = "animal_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private Set<Image> animalImages;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Farm farm;

    public Set<Image> getImages() {
        return this.animalImages;
    }

    public void setImages(Set<Image> images) {
        this.animalImages = images;
    }

    // Abstract method that each animal type must implement
    public abstract float calculateFeedingCost();
}