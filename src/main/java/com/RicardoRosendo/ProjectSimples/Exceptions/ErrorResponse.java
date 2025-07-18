package com.RicardoRosendo.ProjectSimples.Exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> erros;

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(erros)) {
            erros = new ArrayList<>();
        }
        this.erros.add(new ValidationError(field, message));
    }

    public String toJson(){
        return "{\"status\": " + getStatus() + ", " + "\"message\": \"" + getMessage() + "\"}";
    }

}