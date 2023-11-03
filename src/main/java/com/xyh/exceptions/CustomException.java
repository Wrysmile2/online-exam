package com.xyh.exceptions;

import org.springframework.core.annotation.Order;

@Order
public class CustomException extends RuntimeException{

    public CustomException(String message) {
        super(message);
    }
}
