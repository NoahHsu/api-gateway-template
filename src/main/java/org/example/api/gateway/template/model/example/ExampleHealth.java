package org.example.api.gateway.template.model.example;

public class ExampleHealth {
    private String status;

    public ExampleHealth(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
