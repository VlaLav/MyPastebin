package com.vladsaleev.mypastebin.repo;

import com.vladsaleev.mypastebin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(int id);

}
