package org.example.bai1.service;

import lombok.RequiredArgsConstructor;
import org.example.bai1.model.entity.Employee;
import org.example.bai1.repository.EmployeeRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceCustom implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(employee.getRole().getRoleName()));
        return new User(employee.getUsername(), employee.getPassword(), employee.isStatus(), true, true, true, authorities);
    }
}