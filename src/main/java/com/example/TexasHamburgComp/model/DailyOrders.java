package com.example.TexasHamburgComp.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.List;

@Data
public class DailyOrders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private List<String> itemNames;
    private double price;

    @Autowired
    public DailyOrders(List<String> itemNames,double price){
        this.itemNames = itemNames;
        this.price = price;
    }
}
