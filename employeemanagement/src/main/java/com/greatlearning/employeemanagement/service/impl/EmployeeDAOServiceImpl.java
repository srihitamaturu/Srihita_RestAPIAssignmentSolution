package com.greatlearning.employeemanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.greatlearning.employeemanagement.domain.entity.Employee;
import com.greatlearning.employeemanagement.repository.EmployeeRepository;
import com.greatlearning.employeemanagement.service.EmployeeDAOService;

@Service
public class EmployeeDAOServiceImpl implements EmployeeDAOService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public String addEmployee(Employee employee) {
		employeeRepository.save(employee);
		return "Employee " + employee.getFirstName() + " " + employee.getLastName() + " created";
	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll(Sort.by("id"));
		if (employees != null && employees.size() > 0) {
			return employees;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Table is EMPTY");
		}
	}

	@Override
	public Employee getEmployeeById(Integer id) {
		if (id != null && id != 0) {
			Optional<Employee> tempEmployee = employeeRepository.findById(id);
			if (tempEmployee.isPresent()) {
				return tempEmployee.get();
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Id " + id + " NOT FOUND");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Id cannot be empty or 0");
		}
	}

	@Override
	public List<Employee> getEmployeesByName(String firstName) {
		if (StringUtils.hasLength(firstName)) {
			Employee tempEmployee = new Employee();
			tempEmployee.setFirstName(firstName);
			ExampleMatcher exampleMatcher = ExampleMatcher.matching()
					.withMatcher(firstName, ExampleMatcher.GenericPropertyMatchers.exact())
					.withIgnorePaths("firstName, id");
			Example<Employee> example = Example.of(tempEmployee, exampleMatcher);
			List<Employee> employees = employeeRepository.findAll(example, Sort.by("firstName"));
			if (employees != null && employees.size() > 0) {
				return employees;
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Employee with first name " + firstName + " is NOT FOUND");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee First Name cannot be empty");
		}
	}

	@Override
	public List<Employee> getSortedEmployeeList(String sortType) {
		List<Employee> employees;
		// Sort by Descending if requested
		if (sortType.toUpperCase().contains("DESC")) {
			employees = employeeRepository.findAll(Sort.by(Direction.DESC, "firstName"));
		} // Sort by Ascending if requested
		else if (sortType.toUpperCase().contains("ASC")) {
			employees = employeeRepository.findAll(Sort.by(Direction.ASC, "firstName"));
		} // Throw Bad Request error if invalid input
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Invalid Sort Type : " + sortType + "; Valid Sort Type values are ASC and DESC only");
		}
		// Return if DB sent back data else throw error
		if (employees != null && employees.size() > 0) {
			return employees;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Table is EMPTY");
		}
	}

	@Override
	public String updateEmployee(Employee employee) {
		// Use EmployeeId if provided
		if (employee.getId() != null && employee.getId() != 0) {
			Optional<Employee> tempEmployee = employeeRepository.findById(employee.getId());
			if (tempEmployee.isPresent()) {
				Employee employeeFromDb = tempEmployee.get();
				if (StringUtils.hasLength(employee.getFirstName()))
					employeeFromDb.setFirstName(employee.getFirstName());
				if (StringUtils.hasLength(employee.getLastName()))
					employeeFromDb.setLastName(employee.getLastName());
				if (StringUtils.hasLength(employee.getEmail()))
					employeeFromDb.setEmail(employee.getEmail());
				employeeRepository.save(employeeFromDb);
				employeeRepository.flush();
				return "Employee with Id " + employee.getId() + " is updated";
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Employee Id NOT Found. Use valid Employee Id or to create a new employee use POST method");
			}
			// Else get employee by firstName
		} else if (StringUtils.hasLength(employee.getFirstName())) {
			List<Employee> employeesFromDb = getEmployeesByName(employee.getFirstName());
			if (employeesFromDb.size() != 1 && employeesFromDb.get(0) != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Unable to determine Existing Employee to update with given Info. Please use Employee Id to perform update");
			} else {
				Employee employeeFromDb = employeesFromDb.get(0);
				if (StringUtils.hasLength(employee.getFirstName()))
					employeeFromDb.setFirstName(employee.getFirstName());
				if (StringUtils.hasLength(employee.getLastName()))
					employeeFromDb.setLastName(employee.getLastName());
				if (StringUtils.hasLength(employee.getEmail()))
					employeeFromDb.setEmail(employee.getEmail());
				employeeRepository.save(employeeFromDb);
				employeeRepository.flush();
				return "Employee with First Name " + employee.getFirstName() + " is updated";
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Unable to determine Existing Employee to update with given Info. Please use Employee Id to perform update");
		}
	}

	@Override
	public String deleteEmployeeById(Integer id) {
		Optional<Employee> tempEmployee = employeeRepository.findById(id);
		if (tempEmployee.isPresent()) {
			Employee employee = tempEmployee.get();
			employeeRepository.delete(employee);
			return "Employee " + employee.getFirstName() + " " + employee.getLastName() + " deleted";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee Id " + id + " NOT FOUND");
		}
	}

}
