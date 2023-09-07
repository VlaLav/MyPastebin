package com.vladsaleev.mypastebin.service;


import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;
import com.vladsaleev.mypastebin.entity.response.PasteResponse;
import com.vladsaleev.mypastebin.entity.response.PasteUrlResponse;
import com.vladsaleev.mypastebin.exception.PasteNotFoundException;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    public PasteResponse getPasteTextByHash(String hash) {
        String error = "This page is no longer available. It has either expired, " +
                "been removed by its creator, or removed by one of the Pastebin staff.";
        Paste paste = pasteRepository.findByHash(hash).orElseThrow(()-> new PasteNotFoundException(error));
        if (paste.getExpiredTime() != 0 &&
                paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now())){
            throw new PasteNotFoundException(error);
        }
        return new PasteResponse(paste.getText(), paste.getStatus(), paste.getCreatedTime());
    }

    @Override
    public List<PasteResponse> getLastPublicPaste() {
        List<Paste> pasteList = pasteRepository.findLastPublicPaste();
        System.out.println(pasteList);

        return pasteList.stream()
                .filter(paste -> paste.getExpiredTime() == 0 ||
                        !paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now()))
                .map(paste -> new PasteResponse(paste.getText(), paste.getStatus(), paste.getCreatedTime()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public PasteResponse updatePaste(String hash, PasteCreateRequest pasteCreateRequest, Principal principal) {
        String error = "This page is no longer available. It has either expired, " +
                "been removed by its creator, or removed by one of the Pastebin staff.";
        Paste paste = pasteRepository.findByHash(hash).orElseThrow();

        paste.setText(pasteCreateRequest.getText());
        paste.setStatus(pasteCreateRequest.getStatus());
        paste.setExpiredTime(pasteCreateRequest.getExpiredTime());

        Paste paste1 = pasteRepository.save(paste);

        return new PasteResponse(paste1.getText(), paste1.getStatus(), paste1.getCreatedTime());
    }
}
