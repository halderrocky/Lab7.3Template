package edu.sdccd.cisc191.template;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String operation; // e.g., "GET", "SET"
    private int index;
    private int value; // Used for SET operation

    public Request(String operation, int index, int value) {
        this.operation = operation;
        this.index = index;
        this.value = value;
    }

    public String getOperation() {
        return operation;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }
}
