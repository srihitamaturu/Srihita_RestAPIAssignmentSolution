package com.greatlearning.employeemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/403")
public class AccessDeniedPageController {
	
	@GetMapping
	public String getAccessDenied() {
		throw new ResponseStatusException(HttpStatus.FORBIDDEN,
				"You don't have access to perform this GET operation. Only Admin has the necessary privilages");
	}
	
	@PostMapping
	public String postAccessDenied() {
		throw new ResponseStatusException(HttpStatus.FORBIDDEN,
				"You don't have access to perform this POST operation. Only Admin has the necessary privilages");
	}
	
	@PutMapping
	public String putAccessDenied() {
		throw new ResponseStatusException(HttpStatus.FORBIDDEN,
				"You don't have access to perform this PUT operation. Only Admin has the necessary privilages");
	}
	
	@DeleteMapping
	public String deleteAccessDenied() {
		throw new ResponseStatusException(HttpStatus.FORBIDDEN,
				"You don't have access to perform this DELETE operation. Only Admin has the necessary privilages");
	}
}
