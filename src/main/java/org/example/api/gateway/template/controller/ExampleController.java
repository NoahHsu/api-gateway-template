package org.example.api.gateway.template.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.api.gateway.template.model.example.ExampleRequest;
import org.example.api.gateway.template.model.example.ExampleResponse;
import org.example.api.gateway.template.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.example.api.gateway.template.constants.ApiConstants.EXAMPLE_URL;

@RestController
@SecurityRequirement(name = "header-authorization")
public class ExampleController implements BaseController {
    private final ExampleService exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @PostMapping(EXAMPLE_URL)
    @ResponseStatus(HttpStatus.OK)
    public ExampleResponse examplePost(@RequestBody @Valid ExampleRequest request) {
        return exampleService.examplePost(request);
    }

    @GetMapping(EXAMPLE_URL)
    @ResponseStatus(HttpStatus.OK)
    public ExampleResponse exampleGet() {
        return exampleService.exampleGet();
    }

}
