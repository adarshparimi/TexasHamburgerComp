package com.example.TexasHamburgComp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
public class ThcReservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long reserveId;
    private long locationId;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate reserveDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime reserveTime;
}
