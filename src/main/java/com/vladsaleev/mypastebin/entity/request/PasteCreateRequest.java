package com.vladsaleev.mypastebin.entity.request;


import com.vladsaleev.mypastebin.entity.PublicStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteCreateRequest {
    private String text;
    private PublicStatus status;
    private long expiredTime;
}
