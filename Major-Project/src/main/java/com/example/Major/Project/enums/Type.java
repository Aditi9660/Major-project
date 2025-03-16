package com.example.Major.Project.enums;

import lombok.Getter;

@Getter

public enum Type {

    Wooden_Box("Wooden_Box"),
    Cartoon("Cartoon"),
    Envelop("Envelop"),
    Roll("Roll");

    private final String desc;

    Type(String desc) {
        this.desc = desc;
    }
}
