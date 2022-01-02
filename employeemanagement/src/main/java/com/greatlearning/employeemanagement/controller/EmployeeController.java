package com.greatlearning.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.employeemanagement.domain.entity.Employee;
import com.greatlearning.employeemanagement.service.EmployeeDAOService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeDAOService employeeDAOService;
	
	@PostMapping
	public String addEmployee(@RequestBody Employee employee) {
		return employeeDAOService.addEmployee(employee);
	}
	
	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeDAOService.getAllEmployees();
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable Integer id) {
		return employeeDAOService.getEmployeeById(id);
	}
	
	@GetMapping("/search/{name}")
	public List<Employee> getEmployeesByName(@PathVariable String name) {
		return employeeDAOService.getEmployeesByName(name);
	}
	
	@GetMapping("/sort")
	public List<Employee> getSortedEmployeeList(@RequestParam String order) {
		return employeeDAOService.getSortedEmployeeList(order);
	}
	
	@PutMapping
	public String updateEmployee(@RequestBody Employee employee) {
		return employeeDAOService.updateEmployee(employee);
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmployeeById(@PathVariable Integer id) {
		return employeeDAOService.deleteEmployeeById(id);
	}
}
