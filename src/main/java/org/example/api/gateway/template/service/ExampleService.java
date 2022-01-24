package org.example.api.gateway.template.service;

import org.example.api.gateway.template.client.ExampleClient;
import org.example.api.gateway.template.model.example.ExampleHealth;
import org.example.api.gateway.template.model.example.ExampleRequest;
import org.example.api.gateway.template.model.example.ExampleResponse;
import org.example.api.gateway.template.utils.CustomRetryCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class ExampleService {
    private final ExampleClient exampleClient;
    private static final String EXAMPLE_INPUT = "exampleInput";

    @Autowired
    public ExampleService(ExampleClient exampleClient) {
        this.exampleClient = exampleClient;
    }

    public ExampleResponse examplePost(ExampleRequest input) {

        final Response<ExampleHealth> response =
                CustomRetryCallUtils.retryCall(exampleClient.getHealth(), 1, e -> "UP".equals(e.getStatus()))
                                    .orElseThrow();
        return new ExampleResponse(input.getInput(),response.body().getStatus());
    }

    public ExampleResponse exampleGet() {
        final ExampleRequest request = new ExampleRequest(EXAMPLE_INPUT);
        final Response<ExampleResponse> response =
                CustomRetryCallUtils.retryCall(exampleClient.postExample(request), 1,
                                               e -> EXAMPLE_INPUT.equals(e.getInput()))
                                    .orElseThrow();
        return response.body();
    }
}
