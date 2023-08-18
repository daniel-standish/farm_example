package com.standish.farm.persistence.entities;

import jakarta.persistence.*;

import java.util.Random;

@MappedSuperclass
public class Temporal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected int age;

    @Column(nullable = false)
    protected int health;

    @Column(nullable = false)
    protected int value;

    public Temporal(int value) {
        age = new Random().nextInt(50);
        health = 100;
        this.value = value;
    }

    public void progress() {
        progressAge();
        progressHealth();
        calculateValue();
    }

    protected void progressAge() {
        age++;
        age = Math.min(age, 1200);
    }

    protected void progressHealth() {
        if(health > 0) { // child object must be alive
            int delta = new Random().nextInt(20) - 10;
            health += delta;
            health = Math.min(health, 100);
            health = Math.max(health, 0);
        }
    }

    protected void calculateValue() {
        if(health == 0) {
            value = 0;
        } else {
            value += (value * new Random().nextDouble(0.2));
        }
    }

}
