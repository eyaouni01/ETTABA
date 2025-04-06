package com.intern.backendettaba.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

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

    private String type;
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
            joinColumns = {@JoinColumn(name = "animal_id")},inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private Set<Image> animalImages;

    @ManyToOne
    //Adding the name
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    @ManyToOne
    //Adding the name
    @JoinColumn(name = "farm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Farm farm;
}
