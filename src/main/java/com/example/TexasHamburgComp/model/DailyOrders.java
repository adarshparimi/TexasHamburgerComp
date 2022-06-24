package com.example.TexasHamburgComp.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class DailyOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private List<String> itemNames;
    private double price;

    public DailyOrders(List<String> itemNames,double price){
        this.itemNames = itemNames;
        this.price = price;
    }
}
