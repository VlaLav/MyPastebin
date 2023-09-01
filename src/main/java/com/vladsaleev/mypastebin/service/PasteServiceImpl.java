package com.vladsaleev.mypastebin.service;


import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.exception.PasteNotFoundException;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PasteServiceImpl implements PasteService{

    private PasteRepository pasteRepository;

    @Override
    public String savePaste(Paste paste) {
        paste.setCreatedTime(LocalDateTime.now());
        paste.setHash(String.valueOf(Objects.hashCode(paste)));

        System.out.println(paste.getHash());

        Paste paste1 = pasteRepository.save(paste);

        System.out.println(paste1);

        return paste1.getHash();
    }

    @Override
    public String getPasteTextByHash(String hash) {
        Paste paste = pasteRepository.findByHash(hash)
                .orElseThrow(()-> new PasteNotFoundException("Paste by hash " + hash + " was not found"));
        return paste.getText();
    }

    @Override
    public List<Paste> getLastPublicPaste() {
//        pasteRepository.findLastPublicPaste();
        return null;
    }
}
