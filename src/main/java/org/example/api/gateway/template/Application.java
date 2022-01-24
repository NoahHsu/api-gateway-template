package org.example.api.gateway.template;

import org.example.api.gateway.template.constants.ApiConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(name = "header-authorization", scheme = "basic", type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER, paramName = ApiConstants.AUTHORIZATION_HEADER)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
