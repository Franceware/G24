package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder())
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, role from users where username=?")
			;
	}
	
	//@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/consoleadmin").hasRole("ADMIN")
//			.anyRequest().authenticated()
//			.and()
//			.formLogin().permitAll()
//			.and()
//			.logout().permitAll()
//			.and()
//			.exceptionHandling().accessDeniedPage("/403")
//			;
//	}
	
	@Bean
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.requestMatchers("/consoleadmin/*").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin().permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/403")
			;
	}
	
}
