package com.kakaopay.cafeservice.exception;

import com.kakaopay.cafeservice.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse.Builder()
            .setCode(HttpStatus.BAD_REQUEST.value())
            .setMsg(e.getMessage())
            .setFieldErrorData(e.getAllErrors())
            .build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse.Builder()
            .setCode(HttpStatus.BAD_REQUEST.value())
            .setMsg(e.getMessage())
            .build();
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidParameterException(InvalidParameterException e) {
        return new ErrorResponse.Builder()
            .setCode(HttpStatus.BAD_REQUEST.value())
            .setMsg(e.getMessage())
            .setFieldErrorData(e.getErrors())
            .build();
    }

    @ExceptionHandler(value = NotExistUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotExistUserException(NotExistUserException e) {
        return new ErrorResponse.Builder()
            .setCode(HttpStatus.NOT_FOUND.value())
            .setMsg(e.getMessage())
            .build();
    }

    @ExceptionHandler(value = NotExistMenuException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotExistMenuException(NotExistMenuException e) {
        return new ErrorResponse.Builder()
            .setCode(HttpStatus.NOT_FOUND.value())
            .setMsg(e.getMessage())
            .build();
    }

    @ExceptionHandler(value = NotEnoughPointException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotEnoughPointException(NotEnoughPointException e) {
        return new ErrorResponse.Builder()
            .setCode(HttpStatus.BAD_REQUEST.value())
            .setMsg(e.getMessage())
            .build();
    }

}
