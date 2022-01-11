package com.sion.jwtlogintest.repository;

import com.sion.jwtlogintest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String  username);
}
