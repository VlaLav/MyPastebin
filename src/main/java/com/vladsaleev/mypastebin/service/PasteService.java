package com.vladsaleev.mypastebin.service;

import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;

import java.util.List;


public interface PasteService {
    String savePaste(PasteCreateRequest pasteCreateRequest);
    String getPasteTextByHash(String hash);

    List<Paste> getLastPublicPaste();
}
