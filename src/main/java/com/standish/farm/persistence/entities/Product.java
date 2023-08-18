package com.standish.farm.persistence.entities;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private final String name;

    @Column
    private String type;

    @Column
    private int price;

    public Product() {
        this.name = "";
        this.type = "";
        this.price = 0;
    }

    public Product(String name, String type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public void updatePrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

}
