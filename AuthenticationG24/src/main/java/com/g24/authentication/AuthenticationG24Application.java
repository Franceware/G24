package com.g24.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

import com.g24.authentication.repository.UserRepository;
import com.g24.authentication.repository.RoleRepository;
import com.g24.authentication.entity.User;
import com.g24.authentication.entity.Role;

@SpringBootApplication
public class AuthenticationG24Application 
{
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationG24Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder ) 
	{ 
		return args -> {
				if(roleRepository.findByName("ROLE_ADMIN")==null) 
					roleRepository.save(new Role(null,"ROLE_ADMIN"));

				if(roleRepository.findByName("ROLE_USER")==null) 
					roleRepository.save(new Role(null,"ROLE_USER"));

				if(userRepository.findByEmail("admin@admin")==null) 
					userRepository.save(new User(null,"ADMIN_ADMIN", "admin@admin", passwordEncoder.encode("admin"), "", "",
						        Arrays.asList(roleRepository.findByName("ROLE_ADMIN"),roleRepository.findByName("ROLE_USER")))); }; 
	}

}




