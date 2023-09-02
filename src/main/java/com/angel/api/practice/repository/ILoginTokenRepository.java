package com.angel.api.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angel.api.practice.model.LoginToken;

@Repository
public interface ILoginTokenRepository extends JpaRepository<LoginToken, Integer> {
    // You can define custom query methods here if needed
}
