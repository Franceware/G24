package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;



@SpringBootApplication
public class ApplicationG24Application {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationG24Application.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner( @Autowired UserRepository userRepository,
			@Autowired RoleRepository roleRepository, @Autowired PasswordEncoder passwordEncoder ) { return args -> {
				if(roleRepository.findByName("ROLE_ADMIN")==null) roleRepository.save(new Role(null,
						"ROLE_ADMIN"));

				if(roleRepository.findByName("ROLE_USER")==null) roleRepository.save(new Role(null,
						"ROLE_USER"));

				if(userRepository.findByEmail("admin@admin")==null) userRepository.save(new User(null,
						"ADMIN_ADMIN", "admin@admin", passwordEncoder.encode("admin"), "", "",
						Arrays.asList(roleRepository.findByName("ROLE_ADMIN"),roleRepository.findByName("ROLE_USER")))); }; }

}
