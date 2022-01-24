package org.example.api.gateway.template.controller;

import static org.example.api.gateway.template.constants.ApiConstants.BASE_URL;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public interface BaseController {

}

