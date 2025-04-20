package com.intern.backendettaba.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CHICKEN")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Chicken extends Animal {

    private Integer eggsPerWeek;

    @Override
    public float calculateFeedingCost() {
        return 2.5f * getAge() / 10;
    }
}