package com.g24.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class PasswordResetToken
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(nullable = false, unique = true)
	private String token;
    
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	

    @Column(nullable = false)
	private Date expiryDate;

    public void setExpiryDate(int minutes)
    {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }

    public boolean isExpired() 
    {
        return new Date().after(this.expiryDate);
    }	
}
