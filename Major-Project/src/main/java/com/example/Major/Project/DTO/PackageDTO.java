package com.example.Major.Project.DTO;

import com.example.Major.Project.Models.Address;
import com.example.Major.Project.Models.User;
import com.example.Major.Project.enums.DeliveryType;
import com.example.Major.Project.enums.PaymentType;
import com.example.Major.Project.enums.Type;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {
    private Long packageId;

    @NotNull(message = "Weight cannot be null")
    @Min(value = 1, message = "Weight must be greater than 0")
    private Double weight;

    @NotNull(message = "Package Type cannot be null")
    private Type type;

    private Double length;
    private Double breadth;
    private Double height;

    @NotBlank(message = "Please enter details of the package")
    private String details;

    @NotNull(message = "Please enter the average cost of the package")
    @Min(value = 0, message = "Cost must be non-negative")
    private Double costOfPackage;

    private AddressDTO senderAddress;
    private AddressDTO receiverAddress;
    private DeliveryType deliveryType;

    private Double deliveryCost;
    private Date pickUpDate;
    private PaymentType paymentType;
    private Long userId;
    private Long trackingId;



    public PackageDTO(Long packageId, DeliveryType deliveryType) {
    }

    public PackageDTO(Long packageId, double length, double breadth, double height, double weight, double costOfPackage, Type type, String details, double deliveryCost) {
        this.packageId=packageId;
        this.length=length;
        this.breadth=breadth;
        this.height=height;
        this.weight=weight;
        this.type=type;
        this.details=details;
        this.costOfPackage=costOfPackage;
        this.deliveryCost=deliveryCost;
    }

    public PackageDTO(double deliveryCost) {
    }

    public PackageDTO(Long packageId, double deliveryCost, Date pickUpDate, PaymentType paymentType) {
    }
}
