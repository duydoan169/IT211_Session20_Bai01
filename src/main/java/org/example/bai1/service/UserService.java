package org.example.bai1.service;


import lombok.RequiredArgsConstructor;
import org.example.bai1.jwt.JwtService;
import org.example.bai1.model.dto.AuthResponse;
import org.example.bai1.model.dto.LoginDTO;
import org.example.bai1.model.dto.RegisterDTO;
import org.example.bai1.model.entity.Employee;
import org.example.bai1.model.entity.Role;
import org.example.bai1.model.entity.Token;
import org.example.bai1.repository.EmployeeRepository;
import org.example.bai1.repository.RoleRepository;
import org.example.bai1.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiredAccessTime}")
    private String expiredAccessTime;
    @Value("${jwt.expiredRefreshTime}")
    private String expiredRefreshTime;

    public Employee register(RegisterDTO registerDTO) {

        // check username tồn tại
        if (employeeRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // lấy role mặc định
        Role role = roleRepository.findByRoleName("ROLE_CUSTOMER").orElseThrow(() -> new RuntimeException("Role not found"));
        Employee newEmployee = new Employee();
        newEmployee.setUsername(registerDTO.getUsername());
        // mã hóa password
        newEmployee.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newEmployee.setFullName(registerDTO.getFullName());
        newEmployee.setStatus(true);
        newEmployee.setRole(role);
        return employeeRepository.save(newEmployee);
    }

    // ĐĂNG NHẬP
    public AuthResponse login(LoginDTO request) {

        // xác thực tài khoản
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Employee employee = employeeRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("Employee not found"));

        // check trạng thái tài khoản
        if (!employee.isStatus()) {
            throw new RuntimeException("Account disabled");
        }

        // tạo access token
        String accessToken = jwtService.generateAccessToken(employee);

        // tạo refresh token
        String refreshToken = jwtService.generateRefreshToken(employee);

        // revoke token cũ
        revokeAllTokens(employee);

        // lưu access token
        saveToken(employee, accessToken, "ACCESS");

        // lưu refresh token
        saveToken(employee, refreshToken, "REFRESH");

        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).registerUserName(employee.getUsername()).registerFullName(employee.getFullName()).build();
    }

    // LƯU TOKEN
    private void saveToken(Employee employee, String jwt, String tokenType) {
        Token token = new Token();

        token.setEmployee(employee);

        token.setTokenValue(jwt);

        token.setTokenType(tokenType);

        token.setExpired(false);

        token.setRevoked(false);

        tokenRepository.save(token);
    }

    // THU HỒI TOKEN CŨ
    private void revokeAllTokens(Employee employee) {

        List<Token> validTokens = tokenRepository.findAllByEmployeeIdAndExpiredFalseAndRevokedFalse(employee.getId());

        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);
    }
}