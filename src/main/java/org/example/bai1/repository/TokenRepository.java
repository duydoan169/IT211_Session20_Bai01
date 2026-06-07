package org.example.bai1.repository;

import org.example.bai1.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository
        extends JpaRepository<Token, Long> {

    List<Token> findAllByEmployeeIdAndExpiredFalseAndRevokedFalse(Long employeeId);
}