package com.greatlearning.employeemanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.greatlearning.employeemanagement.domain.entity.Role;
import com.greatlearning.employeemanagement.repository.RoleRepository;
import com.greatlearning.employeemanagement.service.RoleDAOService;

@Service
public class RoleDAOServiceImpl implements RoleDAOService {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public String addRole(Role role) {
		if (StringUtils.hasLength(role.getName())) {
			roleRepository.save(role);
			roleRepository.flush();
			return "Role created " + role.getName();
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role Name cannot be empty");
		}
	}

	@Override
	public List<Role> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		if (roles != null && roles.size() > 0) {
			return roles;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role Table is EMPTY");
		}
	}

	@Override
	public Role getRoleById(Integer id) {
		Optional<Role> tempRole = roleRepository.findById(id);
		if (tempRole.isPresent() && tempRole.get().getId() != null && tempRole.get().getId() != 0) {
			return tempRole.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Missing Role Id " + id + ". PLease create using POST method on /roles api");
		}
	}

	@Override
	public Role getRoleByName(String roleName) {
		Role tempRole = new Role();
		tempRole.setName(roleName);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher(roleName, ExampleMatcher.GenericPropertyMatchers.exact()).withIgnorePaths("name, id");
		Example<Role> example = Example.of(tempRole, exampleMatcher);
		List<Role> tempRoles = roleRepository.findAll(example, Sort.by("name"));
		if (tempRoles.size() == 1) {
			return tempRoles.get(0);
		} else if (tempRoles.size() == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Missing Role Name " + roleName + ". PLease create using POST method on /roles api");
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Multiple Role Records with same name " + roleName + ". Please fix the corrupted data");
		}
	}

}
