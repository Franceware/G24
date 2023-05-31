package com.g24.authentication.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.g24.authentication.service.UserService;
import com.g24.authentication.repository.UserRepository;
import com.g24.authentication.repository.RoleRepository;
import com.g24.authentication.dto.UserDto;
import com.g24.authentication.entity.User;
import com.g24.authentication.entity.Role;

//Annotazione Service - Questa classe è un servizio Spring che deve essere analizzato dal framework Spring per l'inserimento delle dipendenze.
@Service
public class UserServiceImpl implements UserService
{
	@Autowired private UserRepository userRepository;
	@Autowired private RoleRepository roleRepository;
	@Autowired private PasswordEncoder passwordEncoder;

	@Override
	public User findUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}	

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
		
		//if(role == null) {role = checkRoleExist();}
		
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}
	
	@Override
	public List<UserDto> findAllUsers()
	{
		List<User> users = userRepository.findAll();
		return users.stream().map((user)->mapToUserDto(user)).collect(Collectors.toList());
	}

    @Override
    public void updatePassword(String password, Long userId) 
    {
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
	
}