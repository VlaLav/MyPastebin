package com.vladsaleev.mypastebin.controller;

import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;
import com.vladsaleev.mypastebin.entity.response.PasteResponse;
import com.vladsaleev.mypastebin.entity.response.PasteUrlResponse;
import com.vladsaleev.mypastebin.service.PasteServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/paste")
public class PasteController {

    private PasteServiceImpl pasteService;

    @GetMapping("/{hash}")
    public ResponseEntity<PasteResponse> getPasteText(@PathVariable String hash) {
        return new ResponseEntity<>(pasteService.getPasteTextByHash(hash), HttpStatus.OK);
    }

    //TODO principal
    @PostMapping
    public ResponseEntity<PasteUrlResponse> createPaste(@RequestBody PasteCreateRequest paste, Principal principal){
        return new ResponseEntity<>(pasteService.savePaste(paste), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PasteResponse>> getTenLastPublicPaste(){
        return new ResponseEntity<>(pasteService.getLastPublicPaste(), HttpStatus.OK);
    }

    @PutMapping("/edit/{hash}")
    public ResponseEntity<PasteResponse> updatePaste(@PathVariable String hash,
                                                     @RequestBody PasteCreateRequest pasteCreateRequest,
                                                     Principal principal){
        return new ResponseEntity<>(pasteService.updatePaste(hash, pasteCreateRequest, principal), HttpStatus.OK);
    }

    //если пользователь не аутентифицирован то перенаправлять с /edit в контроллер аутентификации
    //если пользователь аутентифицирован и авторизован то дать доступ к редактированию
    //если пользователь аутентифицирован и не авторизован то выдать ошибку доступа
}
