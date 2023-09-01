package com.vladsaleev.mypastebin.service;

import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.exception.PasteHasExpiredException;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PasteService {
    public String savePaste(Paste paste);
    public String getPasteTextByHash(String hash) throws PasteHasExpiredException;

    public List<Paste> getLastPublicPaste();
}
