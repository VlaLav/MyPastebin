package com.vladsaleev.mypastebin.exception;

public class PasteHasExpiredException extends Throwable {
    public PasteHasExpiredException(String message) {
        super(message);
    }
}
