package com.example.TexasHamburgComp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Data
@Entity
public class ThcMenuItem {
    @Id
    private String itemId;
    @Column(unique = true)
    private String itemName;
    private double price;
    private double comboPrice;

    public ThcMenuItem(){this.itemId = UUID.randomUUID().toString();}
}

/*
    {
        "itemName":"Dosa",
        "price":10.0,
        "comboPrice":20.0
    }
 */