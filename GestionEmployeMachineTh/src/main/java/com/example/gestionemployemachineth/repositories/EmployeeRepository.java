package com.example.gestionemployemachineth.repositories;

import com.example.gestionemployemachineth.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
}
