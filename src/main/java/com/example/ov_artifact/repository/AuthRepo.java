package com.example.ov_artifact.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ov_artifact.entity.SystemUsers;

@Repository
public interface AuthRepo extends JpaRepository<SystemUsers, String> {
    Optional<SystemUsers> findByName(String name);

    Optional<SystemUsers> findByEmail(String email);

    boolean existsByEmail(String email);
}
