package com.vladsaleev.mypastebin.service;


import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.entity.PublicStatus;
import com.vladsaleev.mypastebin.entity.User;
import com.vladsaleev.mypastebin.entity.request.PasteCreateRequest;
import com.vladsaleev.mypastebin.entity.response.PasteMessageResponse;
import com.vladsaleev.mypastebin.entity.response.PasteResponse;
import com.vladsaleev.mypastebin.entity.response.PasteUrlResponse;
import com.vladsaleev.mypastebin.exception.PasteEditException;
import com.vladsaleev.mypastebin.exception.PasteNotFoundException;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import com.vladsaleev.mypastebin.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PasteServiceImpl implements PasteService{

    private final PasteRepository pasteRepository;
    private final UserRepository userRepository;

    @Override
    public PasteUrlResponse createPaste(PasteCreateRequest pasteCreateRequest, Principal principal) {
        if (pasteCreateRequest.getStatus() == PublicStatus.PRIVATE && principal == null){
            throw new PasteNotFoundException("Please login to Pastebin to create a private paste.");
        }

        Paste paste = new Paste();

        paste.setText(pasteCreateRequest.getText());
        paste.setStatus(pasteCreateRequest.getStatus());
        paste.setExpiredTime(pasteCreateRequest.getExpiredTime());
        paste.setCreatedTime(LocalDateTime.now());
        paste.setHash(Integer.toHexString(Objects.hashCode(paste)));

        if (principal != null){
            paste.setUser(userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        }

        Paste createdPaste = pasteRepository.save(paste);
        return new PasteUrlResponse("https://mypastebin.com/" + createdPaste.getHash());
    }

    @Override
    public PasteResponse getPasteTextByHash(String hash, Principal principal) {
        User user = null;
        if (principal != null){
            user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        String error = "This page is no longer available. It has either expired, " +
                "been removed by its creator, or removed by one of the Pastebin staff.";
        String privateError = "Error, this is a private paste or is pending moderation. " +
                "If this paste belongs to you, please login to Pastebin to view it.";
        Paste paste = pasteRepository.findByHash(hash).orElseThrow(()-> new PasteNotFoundException(error));
        if (paste.getExpiredTime() != 0 &&
                paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now())){
            throw new PasteNotFoundException(error);
        }
        if (paste.getStatus() == PublicStatus.PRIVATE && pasteRepository.findPasteByHashAndUser(hash, user).isEmpty()){
            throw new PasteNotFoundException(privateError);
        }
        return new PasteResponse(paste.getText(),
                "https://mypastebin.com/" + paste.getHash(),
                paste.getStatus(),
                paste.getCreatedTime());
    }

    @Override
    public List<PasteResponse> getLastPublicPaste() {
        List<Paste> pasteList = pasteRepository.findLastPublicPaste();
        System.out.println(pasteList);

        return pasteList.stream()
                .filter(paste -> paste.getExpiredTime() == 0 ||
                        !paste.getCreatedTime().plusSeconds(paste.getExpiredTime()).isBefore(LocalDateTime.now()))
                .map(paste -> new PasteResponse(
                        paste.getText(),
                        "https://mypastebin.com/" + paste.getHash(),
                        paste.getStatus(),
                        paste.getCreatedTime()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public PasteResponse updatePaste(String hash, PasteCreateRequest pasteCreateRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Paste paste = pasteRepository.findPasteByHashAndUser(hash, user)
                .orElseThrow(() -> new PasteEditException("Error, you cannot edit this paste that does not belong to you"));

        paste.setText(pasteCreateRequest.getText());
        paste.setStatus(pasteCreateRequest.getStatus());
        paste.setExpiredTime(pasteCreateRequest.getExpiredTime());

        Paste updatedPaste = pasteRepository.save(paste);

        return new PasteResponse(updatedPaste.getText(),
                "https://mypastebin.com/" + updatedPaste.getHash(),
                updatedPaste.getStatus(),
                updatedPaste.getCreatedTime());
    }

    @Override
    public PasteMessageResponse deletePaste(String hash, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Paste paste = pasteRepository.findPasteByHashAndUser(hash, user)
                .orElseThrow(() -> new PasteEditException("Error, you cannot delete this paste that does not belong to you"));

        pasteRepository.delete(paste);

        return new PasteMessageResponse("Paste with HASH " + hash + " has been deleted");
    }
}
