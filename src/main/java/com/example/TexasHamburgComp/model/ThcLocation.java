package com.example.TexasHamburgComp.model;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class ThcLocation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long locationId;
    private String locationName;

}
