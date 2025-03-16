package com.example.Major.Project.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    Prepaid("Prepaid"),
    Postpaid("Postpaid");

    private final String desc;


    PaymentType(String desc) {
        this.desc = desc;
    }
}
