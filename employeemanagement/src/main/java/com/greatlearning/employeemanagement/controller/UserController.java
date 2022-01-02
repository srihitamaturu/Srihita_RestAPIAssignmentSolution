package com.greatlearning.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.employeemanagement.domain.entity.User;
import com.greatlearning.employeemanagement.service.UserDAOService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserDAOService userDAOService;
	
	@PostMapping
	public String addUser(@RequestBody User user) {
		return userDAOService.addUser(user);
	}
	
	@PutMapping
	public String updateUserRoles(@RequestBody User user) {
		return userDAOService.updateUser(user);
	}
}
