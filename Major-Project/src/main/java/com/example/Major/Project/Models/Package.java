package com.example.Major.Project.Models;

import com.example.Major.Project.enums.DeliveryType;
import com.example.Major.Project.enums.PaymentType;
import com.example.Major.Project.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Data
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long packageId;
    private double weight;
    private Type type;
    private double length;
    private double breadth;
    private double height;
    private String details;
    private double costOfPackage;//Approx. cost of goods being shipped
    private DeliveryType deliveryType;
    @Temporal(TemporalType.DATE)
    private Date pickUpDate;
    private PaymentType paymentType;
    private double deliveryCost;



    @OneToOne
    private Address sender;


    @OneToOne
    private Address receiver;

    @OneToOne
    private Tracking tracking;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;







}
