package com.vladsaleev.mypastebin.service;

import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;
import com.vladsaleev.mypastebin.entity.response.PasteMessageResponse;
import com.vladsaleev.mypastebin.entity.response.PasteResponse;
import com.vladsaleev.mypastebin.entity.response.PasteUrlResponse;

import java.security.Principal;
import java.util.List;


public interface PasteService {
    PasteUrlResponse createPaste(PasteCreateRequest pasteCreateRequest, Principal principal);
    PasteResponse getPasteTextByHash(String hash, Principal principal);
    List<PasteResponse> getLastPublicPaste();
    PasteResponse updatePaste(String hash, PasteCreateRequest pasteCreateRequest, Principal principal);
    PasteMessageResponse deletePaste(String hash, Principal principal);
}
