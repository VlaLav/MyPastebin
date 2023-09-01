package com.vladsaleev.mypastebin.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PasteNotFoundException.class)
    @ResponseBody
    public String handleUserNotFoundException(PasteNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(PasteHasExpiredException.class)
    @ResponseBody
    public String handlePasteHasExpiredException(PasteHasExpiredException ex) {
        return ex.getMessage();
    }
}
