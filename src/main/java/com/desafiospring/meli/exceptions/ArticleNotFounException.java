package com.desafiospring.meli.exceptions;

import org.springframework.http.HttpStatus;

public class ArticleNotFounException extends Exception{
    public ArticleNotFounException(String message) {
        super(message);
    }
}
