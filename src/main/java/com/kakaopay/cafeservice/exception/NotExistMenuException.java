package com.kakaopay.cafeservice.exception;

import lombok.Getter;

@Getter
public class NotExistMenuException extends RuntimeException {

    public NotExistMenuException(String message) {
        super(message);
    }
}
