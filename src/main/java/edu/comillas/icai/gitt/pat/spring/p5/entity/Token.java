package edu.comillas.icai.gitt.pat.spring.p5.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
public class Token {

    @Id @GeneratedValue(strategy = GenerationType.UUID) public String id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(optional = false) public AppUser appUser;

}
