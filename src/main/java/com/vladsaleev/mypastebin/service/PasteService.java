package com.vladsaleev.mypastebin.service;

import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;
import com.vladsaleev.mypastebin.entity.response.PasteUrlResponse;

import java.util.List;


public interface PasteService {
    PasteUrlResponse savePaste(PasteCreateRequest pasteCreateRequest);
    String getPasteTextByHash(String hash);

    List<Paste> getLastPublicPaste();
}
