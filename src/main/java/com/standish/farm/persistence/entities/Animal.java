package com.standish.farm.persistence.entities;

import jakarta.persistence.*;

@Entity
public class Animal extends Temporal {

    @Column(length = 25, nullable = false)
    private String name;

    @Column(length = 25, nullable = false)
    private String type;

    public Animal() {
        super(0);
        this.name = "";
        this.type = "";
    }

    public Animal(String name, String type, int value) {
        super(value);
        this.name = name;
        this.type = type;
    }

    public int getValue() {
        return value;
    }
}
