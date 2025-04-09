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

    // Creator pattern methods added by eya ouni
    public Ettaba createEttaba(LocalDate plantationDate, LocalDate readyDate, Float height, Float width, Double price) {
        Ettaba ettaba = new Ettaba();
        ettaba.setPlantationDate(plantationDate);
        ettaba.setReadyDate(readyDate);
        ettaba.setHeight(height);
        ettaba.setWidth(width);
        ettaba.setPrice(price);
        ettaba.setFarm(this);
        ettaba.setCreationDate(LocalDate.now());
        return ettaba;
    }

    public Animal createAnimal(String name, String type, Integer age, Float price, String description, Set<Image> images) {
        Animal animal = new Animal();
        animal.setName(name);
        animal.setType(type);
        animal.setAge(age);
        animal.setPrice(price);
        animal.setDescription(description);
        animal.setImages(images);
        animal.setFarm(this);
        animal.setCreationDate(LocalDate.now());
        return animal;
    }

    public Event createEvent(String name, Float price, String description,
                             LocalDate startDate, LocalDate endDate,
                             Integer numberTickets, Set<Image> images) {
        Event event = new Event();
        event.setName(name);
        event.setPrice(price);
        event.setDescription(description);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setNumberTickets(numberTickets);
        event.setNumberAvailableTickets(numberTickets);
        event.setImages(images);  // Utiliser la méthode que vous avez ajoutée
        event.setFarm(this);
        event.setCreationDate(LocalDate.now());
        return event;
    }
}