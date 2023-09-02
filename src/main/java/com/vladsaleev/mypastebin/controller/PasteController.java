package com.vladsaleev.mypastebin.controller;

import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;
import com.vladsaleev.mypastebin.entity.response.PasteResponse;
import com.vladsaleev.mypastebin.entity.response.PasteUrlResponse;
import com.vladsaleev.mypastebin.service.PasteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PasteController {

    private PasteServiceImpl pasteService;

    @GetMapping("/{hash}")
    public ResponseEntity<PasteResponse> getPasteText(@PathVariable String hash) {
        return new ResponseEntity<>(pasteService.getPasteTextByHash(hash), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PasteUrlResponse> createPaste(@RequestBody PasteCreateRequest paste){
        return new ResponseEntity<>(pasteService.savePaste(paste), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PasteResponse>> getTenLastPublicPaste(){
        return new ResponseEntity<>(pasteService.getLastPublicPaste(), HttpStatus.OK);
    }
}
