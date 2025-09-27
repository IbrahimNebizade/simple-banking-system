package com.company.simplebankingsystem.dao.repository;

import com.company.simplebankingsystem.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(
            value = "SELECT * FROM users u WHERE u.email = :email AND u.user_status IN ('ACTIVE', 'UPDATE')",
            nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

    @Query(
            value = "SELECT * FROM users u WHERE u.fin = :fin AND u.user_status IN ('ACTIVE', 'UPDATE')",
            nativeQuery = true)
    Optional<UserEntity> findByFin(String fin);
}
