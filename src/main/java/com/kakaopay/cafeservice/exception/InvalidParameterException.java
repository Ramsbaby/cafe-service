package com.kakaopay.cafeservice.exception;

import java.util.List;
import lombok.Getter;
import org.springframework.validation.ObjectError;

@Getter
public class InvalidParameterException extends RuntimeException {

    private final List<ObjectError> errors;

    public InvalidParameterException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }
}
