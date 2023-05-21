package com.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lesson {
    private String name;
    private String category;
    private String description;
    private String date;

    public Lesson() {
    }

    public Lesson(String name, String category, String description, String date) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.date = date;
    }
}
