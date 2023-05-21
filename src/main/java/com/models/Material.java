package com.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Material {
    private String id;
    private String path;

    public Material() {
    }

    public Material(String id, String path) {
        this.path = path;
        this.id = id;
    }

}
