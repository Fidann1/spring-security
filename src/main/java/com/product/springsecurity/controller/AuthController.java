package com.product.springsecurity.controller;

import com.product.springsecurity.dto.AuthRequest;
import com.product.springsecurity.dto.UserDto;
import com.product.springsecurity.entity.UserEntity;
import com.product.springsecurity.repository.UserRepository;
import com.product.springsecurity.service.SecurityService;
import com.product.springsecurity.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
   private AuthenticationManager authenticationManager;
   private final UserRepository userRepository;
   private SecurityService securityService;


    public AuthController(SecurityService securityService,UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }

    @GetMapping
    public String welcome(){

        return "Welcome";
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        return securityService.login(authRequest);

    }
    @PostMapping("/register")
    public String registerUser(@RequestBody UserDto userDto) throws Exception {
        return securityService.register(userDto);

    }
}
