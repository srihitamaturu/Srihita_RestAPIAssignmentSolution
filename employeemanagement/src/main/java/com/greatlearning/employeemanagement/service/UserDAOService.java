package com.greatlearning.employeemanagement.service;

import com.greatlearning.employeemanagement.domain.entity.User;

public interface UserDAOService {

	public String addUser(User user);
	public String updateUser(User user);
	
}
