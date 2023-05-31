package com.g24.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g24.authentication.entity.PasswordResetToken;
import com.g24.authentication.entity.User;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> 
{
    PasswordResetToken findByToken(String token);
    
    PasswordResetToken findByUser(User user);
}
