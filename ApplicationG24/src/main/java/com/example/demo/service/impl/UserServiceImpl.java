package com.example.demo.service.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Annotazione Service - Questa classe è un servizio Spring che deve 
 *                       essere analizzato dal framework Spring per
 *                       l'inserimento delle dipendenze.
 */
@Service
public class UserServiceImpl implements UserService
{
	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	
	/*
	 * public UserServiceImpl(UserRepository userRepository, RoleRepository
	 * roleRepository, PasswordEncoder passwordEncoder) { this.userRepository =
	 * userRepository; this.roleRepository = roleRepository; this.passwordEncoder =
	 * passwordEncoder; }
	 */
	
	//saveUser - Crea una nuova entità per salvarla nel DB
	@Override
	public void saveUser(UserDto userDto)
	{
		User user = new User();
		user.setName(userDto.getFirstName()+ "_" + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setDegreeCourse(userDto.getDegreeCourse());
		user.setUniversity(userDto.getUniversity());
		
		//Crittografia della password utilizzando Spring Security
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		Role role = roleRepository.findByName("ROLE_USER");
		
		if(role == null) {role = checkRoleExist();}
		
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}
	
	@Override
	public User findUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	@Override
	public List<UserDto> findAllUsers()
	{
		List<User> users = userRepository.findAll();
		return users.stream().map((user)->mapToUserDto(user)).collect(Collectors.toList());
	}

    @Override
    public void updatePassword(String password, Long userId) {
        userRepository.updatePassword(passwordEncoder.encode(password), userId);
    }
	
	private UserDto mapToUserDto(User user)
	{
		UserDto userDto = new UserDto();
		String[] str = user.getName().split("_");
		userDto.setFirstName(str[0]);
		userDto.setLastName(str[1]);
		userDto.setEmail(user.getEmail());
		return userDto;
	}
	
	private Role checkRoleExist()
	{
		Role role = new Role();
		role.setName("ROLE_USER");
		return roleRepository.save(role);
	}
	
}