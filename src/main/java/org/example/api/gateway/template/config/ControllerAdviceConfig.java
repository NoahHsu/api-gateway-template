package org.example.api.gateway.template.config;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdviceConfig {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected Map<String, Object> handleException(MethodArgumentNotValidException e) {
        final List<String> errors = e.getBindingResult().getFieldErrors().stream()
                                     .map(err -> format("%s.%s: %s", err.getObjectName(), err.getField(),
                                                  err.getDefaultMessage()))
                                     .collect(Collectors.toList());
        e.getBindingResult().getGlobalErrors().stream()
         .map(err -> format("%s: %s", err.getObjectName(), err.getDefaultMessage()))
         .forEach(errors::add);
        return Map.of(
                "code", HttpStatus.BAD_REQUEST.value(),
                "message", "Fields validation failed",
                "errors", errors);
    }
}
