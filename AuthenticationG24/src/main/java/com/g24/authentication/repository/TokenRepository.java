package com.g24.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g24.authentication.entity.Token;
import com.g24.authentication.entity.User;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> 
{
    Token findByToken(String token);
    
    Token findByUser(User user);
}
