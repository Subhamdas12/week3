package com.week2Homework_springboot_learning.week2Homework_springboot_learning.advices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.week2Homework_springboot_learning.week2Homework_springboot_learning.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND).build();
        return buildExceptionResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception exception) {
        ApiError apiError = ApiError.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND).build();
        return buildExceptionResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getAllErrors().stream().map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError apiError = ApiError.builder().status(HttpStatus.BAD_REQUEST).message("Argument not valid")
                .subErrors(errors).build();

        return buildExceptionResponseEntity(apiError);

    }

    public ResponseEntity<ApiResponse<?>> buildExceptionResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }

}
