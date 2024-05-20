package edu.comillas.icai.gitt.pat.spring.p5.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = Orders.class)
    public Orders order;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    public Product product;

    @Column(nullable = false) public Long quantity;

    @Column(nullable = false) public Long detailPrice;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(targetEntity = AppUser.class)
    public AppUser appUser;

    @Column(nullable = false) public String status;
}