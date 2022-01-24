package org.example.api.gateway.template.model.example;


public class ExampleResponse {
    private String input;
    private String status;

    public ExampleResponse(String input, String status) {
        this.input = input;
        this.status = status;
    }

    public ExampleResponse() {
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
