package org.example.api.gateway.template.exception;

public class BadRequestException extends Exception {
    private static final long serialVersionUID = 7172520745948767336L;

    public BadRequestException(String message) {
        super(message);
    }
}
