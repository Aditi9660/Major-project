package com.example.Major.Project.enums;

import lombok.Getter;

@Getter

public enum DeliveryType {
    Standard("Standard"),
    Express("Express"),
    Overnight("Overnight");

    private final String desc;


    DeliveryType(String desc) {
        this.desc = desc;
    }
}
