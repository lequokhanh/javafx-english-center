package com.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    private String id;
    private String name;

    public Student() {

    }

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
