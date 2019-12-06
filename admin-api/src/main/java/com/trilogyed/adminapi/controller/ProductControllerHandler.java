package com.trilogyed.adminapi.controller;

import com.trilogyed.adminapi.exception.IdNotFound;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ProductControllerHandler {
    @ExceptionHandler(value = {IdNotFound.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> handleIdNotFoundException(IdNotFound e, WebRequest request) {
        return buildResponseEntity(e, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private static ResponseEntity<VndErrors> buildResponseEntity(Exception e, WebRequest request, HttpStatus status) {
        VndErrors error = new VndErrors(request.toString(), e.getMessage());
        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, status);
        return responseEntity;
    }
}