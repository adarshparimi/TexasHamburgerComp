package com.example.TexasHamburgComp.model;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class ThcMenuItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int itemId;
    private String itemName;
    private double price;
    private double comboPrice;
}

/*
    {
        "itemName":"Dosa",
        "price":10.0,
        "comboPrice":20.0
    }
 */