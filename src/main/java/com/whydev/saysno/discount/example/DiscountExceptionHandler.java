package com.whydev.saysno.discount.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice("com.whydev.saysno.discount.contoller")
//public class DiscountExceptionHandler {
//    @ExceptionHandler(DiscountRequestValidationException.class)
//    public ResponseEntity<DiscountErrorResponse> handleProductNotFoundException(DiscountRequestValidationException e) {
//        DiscountErrorResponse errorResponse = new DiscountErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getErrors());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }
//}
