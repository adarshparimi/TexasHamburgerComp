package com.example.TexasHamburgComp.model;

import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Data
public class OpenHoursUpdate {
    private String day;
    private OpenHours openHours;
}
