package edu.comillas.icai.gitt.pat.spring.p5.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = AppUser.class)
    public AppUser appUser;

    @Column(nullable = false) public LocalDate orderDate;

    @Column(nullable = false) public Long totalPrice;
}