package edu.comillas.icai.gitt.pat.spring.p5.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @Column(nullable = false) public String productGroup;

    @Column(nullable = false) public String productName;

    @Column(nullable = false) public Long productPrice;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = AppUser.class)
    public AppUser appUser;
}