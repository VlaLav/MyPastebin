package com.vladsaleev.mypastebin.service;


import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.exception.PasteHasExpiredException;
import com.vladsaleev.mypastebin.exception.PasteNotFoundException;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public String getPasteTextByHash(String hash) throws PasteHasExpiredException {
        Paste paste = pasteRepository.findByHash(hash)
                .orElseThrow(()-> new PasteNotFoundException("Paste by hash " + hash + " was not found"));
        if (paste.getExpiredTime() != 0 &&
                paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now())){
            throw new PasteHasExpiredException("Paste by hash " + hash + " has expired");
        }
        return paste.getText();
    }

    @Override
    public List<Paste> getLastPublicPaste() {
        List<Paste> pasteList = pasteRepository.findLastPublicPaste();

        return pasteList.stream()
                .filter(paste -> paste.getExpiredTime() == 0 ||
                        !paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
