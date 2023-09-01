package com.vladsaleev.mypastebin.controller;

import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.service.PasteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PasteController {

    private PasteServiceImpl pasteService;

    @GetMapping("/{hash}")
    public ResponseEntity<String> getPasteText(@PathVariable String hash){
        return new ResponseEntity<String>(pasteService.getPasteTextByHash(hash), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createPaste(@RequestBody Paste paste){
        return new ResponseEntity<>(pasteService.savePaste(paste), HttpStatus.CREATED);
    }


}