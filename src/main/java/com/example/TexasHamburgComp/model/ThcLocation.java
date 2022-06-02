package com.example.TexasHamburgComp.model;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.UUID;

@Data
@Entity
public class ThcLocation {
    @Id
    private String locationId;
    private String locationName;

    public ThcLocation(){

        this.locationId = UUID.randomUUID().toString();
    }

}
