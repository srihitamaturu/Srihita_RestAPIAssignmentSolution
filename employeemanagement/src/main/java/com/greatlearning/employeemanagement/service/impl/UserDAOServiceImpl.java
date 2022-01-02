package com.greatlearning.employeemanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.greatlearning.employeemanagement.domain.entity.Role;
import com.greatlearning.employeemanagement.domain.entity.User;
import com.greatlearning.employeemanagement.repository.UserRepository;
import com.greatlearning.employeemanagement.service.RoleDAOService;
import com.greatlearning.employeemanagement.service.UserDAOService;

@Service
public class UserDAOServiceImpl implements UserDAOService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleDAOService roleDAOService;

	@Override
	public String addUser(User user) {
		if (StringUtils.hasLength(user.getUsername()) && StringUtils.hasLength(user.getPassword())
				&& user.getRoles().size() > 0 && StringUtils.hasLength(user.getRoles().get(0).getName())) {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			// Set Role id if empty
			validateRoleData(user);
			userRepository.save(user);
			userRepository.flush();
			return "User " + user.getUsername() + " created";
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username / Password / Roles cannot be empty");
		}

	}

	@Override
	public String updateUser(User user) {
		User userFromDb;
		// User UserId if provided
		if (user.getUserId() != null && user.getUserId() != 0) {
			Optional<User> tempUser = userRepository.findById(user.getUserId());
			if (tempUser.isPresent()) {
				userFromDb = tempUser.get();
				if (StringUtils.hasLength(user.getUsername()))
					userFromDb.setUsername(user.getUsername());
				if (StringUtils.hasLength(user.getPassword()))
					userFromDb.setPassword(user.getPassword());
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"User Id NOT Found. Use valid User Id or to create a new User use POST method");
			}
			// Else get user by username
		} else if (StringUtils.hasLength(user.getUsername())) {
			User tempUser = new User();
			tempUser.setUsername(user.getUsername());
			ExampleMatcher exampleMatcher = ExampleMatcher.matching()
					.withMatcher(user.getUsername(), ExampleMatcher.GenericPropertyMatchers.exact())
					.withIgnorePaths("username, userId");
			Example<User> example = Example.of(tempUser, exampleMatcher);
			List<User> tempUsers = userRepository.findAll(example, Sort.by("username"));
			if (tempUsers.size() == 1) {
				userFromDb = tempUsers.get(0);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Unable to determine User with input ptovided. Please use User Id to update User");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Unable to determine User with input ptovided. Please use User Id to update User");
		}
		// Set Roles if NOT empty
		validateRoleData(user);
		if (user.getRoles().size() > 0) {
			userFromDb.setRoles(user.getRoles());
		}
		userRepository.save(userFromDb);
		userRepository.flush();
		return "User " + user.getUsername() + " updated";
	}

	public void validateRoleData(User user) {
		for (Role tempRole : user.getRoles()) {
			if (tempRole.getId() == null || tempRole.getId() == 0) {
				if (StringUtils.hasLength(tempRole.getName())) {
					Role roleFromDB = roleDAOService.getRoleByName(tempRole.getName());
					if (roleFromDB != null) {
						tempRole.setId(roleFromDB.getId());
					}
				} else {
					user.getRoles().remove(tempRole);
				}
			}
		}
	}

}
