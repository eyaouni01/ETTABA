package com.intern.backendettaba.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float longitude;

    private Float latitude;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "farm_images",
            joinColumns = {@JoinColumn(name = "farm_id")},inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private Set<Image> farmImages;

    @Column(columnDefinition = "TEXT")
    private String description;


    private LocalDate creationDate;

    /*
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "farm")
    private List<Event> events;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "farm")
    private List<Ettaba> ettabas;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "farm")
    private List<Animal> animals;
    */
}
