package com.kakaopay.cafeservice.exception;

import lombok.Getter;

@Getter
public class NotEnoughPointException extends RuntimeException {

    public NotEnoughPointException(String message) {
        super(message);
    }
}
