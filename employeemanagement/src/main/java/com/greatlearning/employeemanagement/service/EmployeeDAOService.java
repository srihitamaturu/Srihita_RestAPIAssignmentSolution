package com.greatlearning.employeemanagement.service;

import java.util.List;

import com.greatlearning.employeemanagement.domain.entity.Employee;

public interface EmployeeDAOService {

	public String addEmployee(Employee employee);

	public List<Employee> getAllEmployees();

	public Employee getEmployeeById(Integer id);
	
	public List<Employee> getEmployeesByName(String name);

	public List<Employee> getSortedEmployeeList(String sortType);

	public String updateEmployee(Employee employee);

	public String deleteEmployeeById(Integer id);

}
