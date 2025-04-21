package com.intern.backendettaba.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("COW")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Cow extends Animal {

    private Float milkPerDay;

    @Override
    public float calculateFeedingCost() {
        return 15.0f * getAge() / 10;
    }
}