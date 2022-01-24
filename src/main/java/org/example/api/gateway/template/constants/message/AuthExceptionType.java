package org.example.api.gateway.template.constants.message;

import static org.example.api.gateway.template.constants.ApiConstants.AUTHORIZATION_HEADER;

public enum AuthExceptionType {
    NO_TOKEN("Header didn't contains token in " + AUTHORIZATION_HEADER),
    ;
    private final String message;

    AuthExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
