package edu.comillas.icai.gitt.pat.spring.p5.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.security.Timestamp;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public String id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = AppUser.class)
    public AppUser appUser;

    @Column(nullable = false) public Timestamp orderDate;

    @Column(nullable = false) public Long totalPrice;
}