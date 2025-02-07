package com.tea.demo.product.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeaNotFoundException extends RuntimeException {
    public TeaNotFoundException() {
        super("Specify tea not found / tea is empty ",null,true,false);
    }
}
