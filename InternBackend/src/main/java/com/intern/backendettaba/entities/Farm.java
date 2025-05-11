package com.intern.backendettaba.entities;

import com.intern.backendettaba.designpattern.factory.AnimalFactory;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
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

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // EAGER au lieu de LAZY
    private Set<Event> events;



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

     /**
     * Create an animal using the Factory pattern
     * This implements the GRASP Creator pattern
     */
    public Animal createAnimal(
            AnimalFactory factory,
            String name,
            Integer age,
            Float price,
            String description,
            Set<Image> images) {
        
        return factory.createAnimalForFarm(name, age, price, description, images, this);
    }

    public Event createEvent(String name, Float price, String description,
                             LocalDate startDate, LocalDate endDate,
                             Integer numberTickets, Set<Image> images , List<Event> ListeventsByfarm) {


        // Vérification de la contrainte de chevauchement
        if (ListeventsByfarm != null) {
            System.out.println("nombre des evemenements"+ListeventsByfarm.size());




            for (Event existingEvent : ListeventsByfarm) {
                boolean chevauchement =
                        startDate.isBefore(existingEvent.getEndDate()) &&
                                endDate.isAfter(existingEvent.getStartDate());
                if (chevauchement) {


                    throw new IllegalStateException("Un événement chevauche déjà cette période dans cette ferme.");
                }
            }
        }
        /**
         * OCL Constraint:
         * context Event
         * inv PasDEvenementsChevauchants:
         *   let f : Farm = self.farm in
         *     f.events->excluding(self)->forAll(e |
         *       self.endDate <= e.startDate or self.startDate >= e.endDate
         *     )
         *
         * Cette méthode vérifie qu’aucun autre événement de la ferme ne chevauche la période.
         */





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
