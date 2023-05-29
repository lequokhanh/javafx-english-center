package com.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {
    public String x;
    public int y;

    public Point() {
    }

    public Point(String x, int y) {
        this.x = x;
        this.y = y;
    }
}
