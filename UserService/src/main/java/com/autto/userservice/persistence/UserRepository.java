package com.autto.userservice.persistence;

import com.autto.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, byte[]>  {
    boolean existsByEmail(String email);
    Optional<User> getByEmail(String email);
}
