package com.tea.demo.product;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeaNotFoundException extends RuntimeException {
    public TeaNotFoundException() {
        super("tea not found");
    }
}
