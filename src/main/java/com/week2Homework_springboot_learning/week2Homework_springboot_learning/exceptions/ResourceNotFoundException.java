package com.week2Homework_springboot_learning.week2Homework_springboot_learning.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
