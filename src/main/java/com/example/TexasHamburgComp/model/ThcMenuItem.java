package com.example.TexasHamburgComp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class ThcMenuItem {
    @Id
    private String itemId;
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