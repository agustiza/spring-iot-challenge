package com.iotchallenge.sim.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Sim {

    public Sim(String simId, String country, boolean active) {
        this.simId = simId;
        this.country = country;
        this.active = active;
    }

    protected Sim() {}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String simId;

    private String country;

    private boolean active;
}
