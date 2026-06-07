package org.example.bai1.repository;

import jakarta.validation.constraints.NotBlank;
import org.example.bai1.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByUsername(@NotBlank String username);

    Optional<Employee> findByUsername(String username);
}
