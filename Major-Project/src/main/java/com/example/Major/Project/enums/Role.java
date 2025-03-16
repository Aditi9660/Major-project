package com.example.Major.Project.enums;

import lombok.Getter;

@Getter

public enum Role {
        USER("User"),
        AGENT("Agent"),
        ADMIN("Admin");


        private final String desc;


    Role(String desc) {
            this.desc = desc;
    }



}
