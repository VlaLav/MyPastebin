package com.vladsaleev.mypastebin.repo;

import com.vladsaleev.mypastebin.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasteRepository extends JpaRepository <Paste, Integer> {
    public Paste findByHash(String hash);
    public List<Paste> findLastPublicPaste();

}
