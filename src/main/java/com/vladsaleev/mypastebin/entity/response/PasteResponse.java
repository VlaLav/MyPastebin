package com.vladsaleev.mypastebin.entity.response;

import com.vladsaleev.mypastebin.entity.PublicStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasteResponse {
    private String text;
    private PublicStatus status;
    private LocalDateTime createdTime;
}
