package com.vladsaleev.mypastebin.repo;

import com.vladsaleev.mypastebin.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository <Paste, Integer> {
    public Optional<Paste> findByHash(String hash);
    @Query(value = "SELECT p FROM Paste p WHERE p.status = 'public' ORDER BY p.createdTime DESC LIMIT 10")
    public List<Paste> findLastPublicPaste();

}
