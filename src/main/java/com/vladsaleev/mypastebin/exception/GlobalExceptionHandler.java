package com.vladsaleev.mypastebin.exception;


import com.vladsaleev.mypastebin.entity.response.PasteMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PasteNotFoundException.class)
    @ResponseBody
    public ResponseEntity<PasteMessageResponse> handlePasteNotFoundException(PasteNotFoundException ex) {
        return new ResponseEntity<>(new PasteMessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasteEditException.class)
    @ResponseBody
    public ResponseEntity<PasteMessageResponse> handlePasteNotFoundException(PasteEditException ex) {
        return new ResponseEntity<>(new PasteMessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseBody
    public ResponseEntity<PasteMessageResponse> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return new ResponseEntity<>(new PasteMessageResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }
}
