package com.greatlearning.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.employeemanagement.domain.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
