package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SpringSecurity
{
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	@Bean
	public static PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
	{
		http.csrf(
				csrf -> csrf.disable())
			.authorizeHttpRequests(
					authorize -> authorize
					.requestMatchers("/register/**", 
									"/forgot-password**",
                                    "/reset-password**").permitAll()
					.requestMatchers("/index").permitAll()
					.requestMatchers("/users").hasRole("ADMIN")
					.requestMatchers("/home").hasRole("USER")
					.anyRequest().permitAll()
					)
			.formLogin(
					form -> form
					.loginPage("/login")
					.loginProcessingUrl("/login")
					.permitAll()
					.defaultSuccessUrl("/home")
					)
			.logout(
					logout -> logout
					.logoutUrl("/logout")
					.permitAll()
					.invalidateHttpSession(true)
                    .clearAuthentication(true)
                    //.logoutSuccessUrl("/login?logout")					
					)
			;
		return http.build();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}