package com.autto.userservice.persistence;

import com.autto.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>  {
    boolean existsByUserId(String userId);
}
