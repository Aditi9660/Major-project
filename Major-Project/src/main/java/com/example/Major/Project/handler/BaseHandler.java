package com.example.Major.Project.handler;

import com.example.Major.Project.DTO.PackageDTO;
import com.example.Major.Project.enums.DeliveryType;

import java.util.Random;

public class BaseHandler {
    public String generateTrackingNo(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder tracking = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {  // Generate 6 characters
            tracking.append(chars.charAt(random.nextInt(chars.length())));
        }
        return tracking.toString();
    }



    public double cost(PackageDTO packageDTO) {
       // PackageDTO packageDTO = new PackageDTO();
        double weight = packageDTO.getWeight();
        DeliveryType deliveryType = packageDTO.getDeliveryType();
        double gst = 20 * 0.18;
        double gstPerKg = 40 * 0.18;
        double totalCost;

        if (weight <= 0.5) {
            totalCost = (weight * 20.0) + gst;
        } else {
            totalCost = (weight * 40.0) + gstPerKg;
        }

        if(deliveryType == DeliveryType.Standard){
            return totalCost;
        }
        else if (deliveryType == DeliveryType.Express) {
            totalCost = totalCost * 1.25; //25% increment
            return totalCost;
        }
        else if (deliveryType == DeliveryType.Overnight) {
            totalCost *= 1.5;
            return totalCost; //50% increment
        }

        return totalCost;

    }
}
