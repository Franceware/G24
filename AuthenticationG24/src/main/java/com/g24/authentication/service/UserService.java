package com.g24.authentication.service;

import java.util.List;

import com.g24.authentication.dto.UserDto;
import com.g24.authentication.entity.User;

public interface UserService
{
	User findUserByEmail(String email);

	void saveUser(UserDto userDto);

	List<UserDto> findAllUsers();

    void updatePassword(String password, Long userId);
}