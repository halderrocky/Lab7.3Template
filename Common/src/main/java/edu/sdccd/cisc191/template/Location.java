package edu.sdccd.cisc191.template;

import java.io.Serializable;

// Parent class
class Location implements Serializable {
    private String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
