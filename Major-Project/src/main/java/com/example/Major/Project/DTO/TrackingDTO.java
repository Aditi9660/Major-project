package com.example.Major.Project.DTO;

import com.example.Major.Project.Models.Package;
import com.example.Major.Project.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingDTO {
    private String TrackingNo;


    private Status deliveryStatus;

    //@Temporal(TemporalType.TIMESTAMP)
    private Date DeliveryDate;

    private Long packageId;

    public TrackingDTO(String trackingNo, Status deliveryStatus) {
    }






}
