package edu.sdccd.cisc191.template;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private String result;

    public Response(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
