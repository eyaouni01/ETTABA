package com.intern.backendettaba.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

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
    //private List coordinate;
    private Double price;

    private LocalDate creationDate;
    private LocalDate boughtDate;

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

    /*
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ettaba")
    private List<Product> products;*/
}
