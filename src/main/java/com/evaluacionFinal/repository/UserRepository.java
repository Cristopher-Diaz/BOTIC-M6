package com.evaluacionFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evaluacionFinal.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
