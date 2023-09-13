package com.vladsaleev.mypastebin.exception;


import com.vladsaleev.mypastebin.entity.response.PasteErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PasteNotFoundException.class)
    @ResponseBody
    public ResponseEntity<PasteErrorResponse> handlePasteNotFoundException(PasteNotFoundException ex) {
        return new ResponseEntity<>(new PasteErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasteEditException.class)
    @ResponseBody
    public ResponseEntity<PasteErrorResponse> handlePasteNotFoundException(PasteEditException ex) {
        return new ResponseEntity<>(new PasteErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
