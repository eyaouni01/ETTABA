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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Float price;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "event_images",
            joinColumns = {@JoinColumn(name = "event_id")},inverseJoinColumns = {@JoinColumn(name = "image_id")})
    private Set<Image> eventImages;

    @Column(nullable = false)
    private Integer numberTickets;
    @Column(nullable = false)
    private Integer numberAvailableTickets;
    @Transient
    private Integer numberSoldTickets;

    private LocalDate creationDate;
    private LocalDate boughtDate;

    @ManyToOne
    //Adding the name
    @JoinColumn(name = "farm_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Farm farm;
}
