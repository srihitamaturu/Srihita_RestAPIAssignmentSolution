package com.greatlearning.employeemanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.greatlearning.employeemanagement.domain.entity.Role;

@Service
public interface RoleDAOService {
	
	public String addRole(Role role);
	
	public List<Role> getAllRoles();
	
	public Role getRoleById(Integer id);
	
	public Role getRoleByName(String roleName);

}
