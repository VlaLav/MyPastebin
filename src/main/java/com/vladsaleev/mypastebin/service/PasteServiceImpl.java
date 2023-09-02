package com.vladsaleev.mypastebin.service;


import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;
import com.vladsaleev.mypastebin.entity.response.PasteUrlResponse;
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
    public PasteUrlResponse savePaste(PasteCreateRequest pasteCreateRequest) {
        Paste paste = new Paste();

        paste.setText(pasteCreateRequest.getText());
        paste.setStatus(pasteCreateRequest.getStatus());
        paste.setExpiredTime(pasteCreateRequest.getExpiredTime());
        paste.setCreatedTime(LocalDateTime.now());
        paste.setHash(Integer.toHexString(Objects.hashCode(paste)));

        Paste paste1 = pasteRepository.save(paste);
        return new PasteUrlResponse("https://mypastebin.com/" + paste1.getHash());
    }

    @Override
    public String getPasteTextByHash(String hash) {
        String error = "This page is no longer available. It has either expired, " +
                "been removed by its creator, or removed by one of the Pastebin staff.";
        Paste paste = pasteRepository.findByHash(hash).orElseThrow(()-> new PasteNotFoundException(error));
        if (paste.getExpiredTime() != 0 &&
                paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now())){
            throw new PasteNotFoundException(error);
        }
        return paste.getText();
    }

    @Override
    public List<Paste> getLastPublicPaste() {
        List<Paste> pasteList = pasteRepository.findLastPublicPaste();
        System.out.println(pasteList);

        return pasteList.stream()
                .filter(paste -> paste.getExpiredTime() == 0 ||
                        !paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now()))
                .limit(10)
                .collect(Collectors.toList());
    }
}
