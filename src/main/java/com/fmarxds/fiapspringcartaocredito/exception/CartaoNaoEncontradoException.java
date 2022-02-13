package com.fmarxds.fiapspringcartaocredito.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartaoNaoEncontradoException extends RuntimeException {

    public CartaoNaoEncontradoException(String message) {
        super(message);
    }

    public CartaoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

}
