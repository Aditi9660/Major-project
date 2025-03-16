package com.example.Major.Project.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data

public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long AddressId;
    private String name;
    @Column(length = 10)
    private String mobileNo;
    private String address;
    private int postalCode;
    private String state;
    private String city;
    private boolean senderAddress;
    private boolean receiverAddress;
    //@ManyToOne
    //@JoinColumn(name = "UserId")
    //private User user;

    @ManyToOne
    private Package aPackage;

}
