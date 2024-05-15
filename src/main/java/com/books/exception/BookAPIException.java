package com.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAPIException extends RuntimeException {

    public BookAPIException(String resourceName) {
        super(String.format("%s not found with the given input data %s:'%s'", resourceName));
    }
}
