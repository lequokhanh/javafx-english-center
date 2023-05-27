package com.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lesson {
    private String id;
    private Chapter chapter;
    private String date;

    public Lesson() {
    }

    public Lesson(String id, Chapter chapter, String date) {
        this.id = id;
        this.chapter = chapter;
        this.date = date;
    }
}
