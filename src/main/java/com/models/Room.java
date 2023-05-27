package com.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private String id;
    private String name;
    private String capacity;

    public Room() {
    }

    public Room(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Room(String id, String name, String capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }


    public String toString() {
        return name;
    }
}
