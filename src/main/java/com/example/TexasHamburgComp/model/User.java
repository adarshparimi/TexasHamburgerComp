package com.example.TexasHamburgComp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    private long userId;
    private String username;
    private String password;
    private String userRole;
}
