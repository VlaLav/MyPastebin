package com.vladsaleev.mypastebin.service;

import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PasteService {
    public String savePaste(Paste paste);
    public String getPasteByHash(String hash);
    public List<Paste> getLastPublicPaste();
}
