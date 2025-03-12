package com.marketanalysis.service.postgres.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketanalysis.service.postgres.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
