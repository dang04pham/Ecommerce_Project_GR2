package com.ecommerce.application.security.service;

import com.ecommerce.application.enums.RoleName;
import com.ecommerce.application.model.Role;
import com.ecommerce.application.model.User;
import com.ecommerce.application.repository.RoleRepository;
import com.ecommerce.application.repository.UserRepository;
import com.ecommerce.application.security.dto.request.LoginRequest;
import com.ecommerce.application.security.dto.request.SignUpRequest;
import com.ecommerce.application.security.dto.response.LoginResponse;
import com.ecommerce.application.security.dto.response.SignUpResponse;
import com.ecommerce.application.security.jwt.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, UserDetailsService userDetailsService, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

    }

    public SignUpResponse register(SignUpRequest signUpRequest) throws Throwable {
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if(userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(Set.of(userRole));

        userRepository.save(user);
        return new SignUpResponse(user.getEmail(), "User registered successfully");
    }

    public LoginResponse authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(loginRequest.getUsername());


        String jwtToken = jwtUtils.generateJwtTokenFromUsername(userDetails);
        return new LoginResponse(jwtToken, "Bearer", userDetails.getUsername());
    }


}
