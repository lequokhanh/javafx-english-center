package com.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher {
    private String id;
    private String name;

    public Teacher() {
    }

    public Teacher(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
