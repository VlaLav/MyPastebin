package com.vladsaleev.mypastebin.service;

import com.vladsaleev.mypastebin.entity.Paste;

import java.util.List;


public interface PasteService {
    String savePaste(Paste paste);
    String getPasteTextByHash(String hash);

    List<Paste> getLastPublicPaste();
}
