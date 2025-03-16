package com.example.Major.Project.Models;

import com.example.Major.Project.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data

public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int TrackingId;

    private String TrackingNo;


    private Status deliveryStatus;

    //@Temporal(TemporalType.TIMESTAMP)
    private Date DeliveryDate;

    @OneToOne
    private Package aPackage;


}
