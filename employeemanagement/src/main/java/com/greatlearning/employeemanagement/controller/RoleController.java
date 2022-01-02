package com.greatlearning.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.employeemanagement.domain.entity.Role;
import com.greatlearning.employeemanagement.service.RoleDAOService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	RoleDAOService roleDAOService;

	@PostMapping
	public String addRole(@RequestBody Role role) {
		return roleDAOService.addRole(role);
	}
	
	@GetMapping
	public List<Role> getAllRoles() {
		return roleDAOService.getAllRoles();
	}
}
