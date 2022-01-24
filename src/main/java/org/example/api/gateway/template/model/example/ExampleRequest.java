package org.example.api.gateway.template.model.example;

import javax.validation.constraints.NotNull;

public class ExampleRequest {
    @NotNull
    private String input;

    public ExampleRequest() {
    }

    public ExampleRequest(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
