package com.epam.esm.controller.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    //fixme localize exception
//    @ExceptionHandler(ApplicationRuntimeException.class)
//    public ResponseEntity<RestApplicationError> applicationErrorHandler(ApplicationRuntimeException exception) {
//        RestApplicationError error = new RestApplicationError(exception.getMessage(), exception.getErrorCode());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
//    }

    //    @ExceptionHandler(InvalidFormatException.class)
//    public ResponseEntity<RestApplicationError> invalidRequestBody() {
//        RestApplicationError error = new RestApplicationError("Incorrect data was provided, check your body values",
//                RestErrorStatusCode.VALIDATION_ERROR);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<RestApplicationError> invalidRequestParam() {
//        RestApplicationError error = new RestApplicationError(
//                "Incorrect parameter was provided, check your parameters values",
//                RestErrorStatusCode.VALIDATION_ERROR);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
//    }
//
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<RestApplicationError> defaultErrorHandler() {
//        RestApplicationError error = new RestApplicationError("Bad request", 40403);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(error, httpHeaders, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<RestApplicationError> unknownErrorHandler() {
//        RestApplicationError error = new RestApplicationError("Incorrect request", 40404);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(error, httpHeaders, HttpStatus.NOT_FOUND);
//    }
}
