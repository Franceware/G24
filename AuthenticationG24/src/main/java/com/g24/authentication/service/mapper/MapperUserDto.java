package com.g24.authentication.service.mapper;

import com.g24.authentication.dto.UserDto;
import com.g24.authentication.entity.User;
import com.g24.authentication.entity.Role;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class MapperUserDto
{
	public UserDto toDto(User user)
	{
		return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), 
				                      user.getEmail(), user.getDegreeCourse(), 
				                      user.getUniversity());
	}
	
    public User toUser(UserDto userDTO) {
    	
        return new User(userDTO.getId(), false, (userDTO.getFirstName()+ "_" + userDTO.getLastName()), 
        		        userDTO.getEmail(), "", userDTO.getDegreeCourse(), userDTO.getUniversity(),
        		        new ArrayList<Role>());
    }
}
