package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class RoleServiceImpl implements RoleService {

	@Autowired private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findRoleByName(String name)
	{
		return roleRepository.findByName(name);
	}

}
