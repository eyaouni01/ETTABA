package com.intern.backendettaba.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String state;
    private Integer postalCode;
    private String city;

    @Column(nullable = false)
    private String region;
    private String street;
    private String houseNumber;
}
