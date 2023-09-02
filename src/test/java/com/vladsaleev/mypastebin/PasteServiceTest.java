package com.vladsaleev.mypastebin;

import com.vladsaleev.mypastebin.entity.Paste;
import com.vladsaleev.mypastebin.entity.response.PasteResponse;
import com.vladsaleev.mypastebin.exception.PasteNotFoundException;
import com.vladsaleev.mypastebin.repo.PasteRepository;
import com.vladsaleev.mypastebin.service.PasteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.vladsaleev.mypastebin.entity.PublicStatus.PUBLIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteServiceTest {
    @Autowired
    private PasteService pasteService;

    @MockBean
    private PasteRepository pasteRepository;

    @Test
    public void notExistHash(){
        assertThrows(PasteNotFoundException.class, () -> pasteService.getPasteTextByHash("fagaegeg"));
    }

    @Test
    public void getExistHash(){
        Paste entity = new Paste();
        entity.setCreatedTime(LocalDateTime.of(2000, 11, 12, 11,56));
        entity.setHash("1");
        entity.setText("11");
        entity.setStatus(PUBLIC);

        when(pasteRepository.findByHash("1")).thenReturn(Optional.of(entity));

        PasteResponse expected = new PasteResponse("11", PUBLIC, LocalDateTime.of(2000, 11, 12, 11,56));
        PasteResponse actual = pasteService.getPasteTextByHash("1");

        assertEquals(expected, actual);

    }
}
