package com.example.Major.Project.enums;


import lombok.Getter;

@Getter

public enum Status {
    Booked("Booked"),
    PickedUp("packed"),
    Transit("In Transit"),
    OutForDelivery("OutForDelivery"),
    Delivered("Delivered"),
    Lost("Lost");

    private final String desc;

    Status(String desc) {
        this.desc = desc;
    }
}
