package com.example.TexasHamburgComp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ThcMenuItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long itemId;

    private String itemName;

    private double price;

    private double comboPrice;
    
    public ThcMenuItem(String itemName,double price,double comboPrice){
        this.itemName = itemName;
        this.price = price;
        this.comboPrice = comboPrice;
    }
}

/*
    {
        "itemName":"Dosa",
        "price":10.0,
        "comboPrice":20.0
    }
 */