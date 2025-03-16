package com.example.Major.Project.DTO;

import com.example.Major.Project.enums.DeliveryType;
import com.example.Major.Project.enums.Status;
import com.example.Major.Project.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserTrackingDTO {
    private Long userId;
    private String username;
    private String trackingNo;
    private Status deliveryStatus;
    private Long packageId;
    private DeliveryType deliveryType;



}

