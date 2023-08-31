package com.vladsaleev.mypastebin.service;


import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class PasteServiceImpl implements PasteService{
    private PasteRepository pasteRepository;

    @Override
    public String savePaste(Paste paste) {
        paste.setHash(String.valueOf(Objects.hashCode(paste)));
        System.out.println(paste.getHash());
        Paste paste1 = pasteRepository.save(paste);
        return paste1.getHash();
    }

    @Override
    public String getPasteTextByHash(String hash) {
        Paste paste = pasteRepository.findByHash(hash);
        return paste.getText();
    }

    @Override
    public List<Paste> getLastPublicPaste() {
        return pasteRepository.findLastPublicPaste();
    }
}
