package com.product.springsecurity.controller;

import com.product.springsecurity.dto.AuthRequest;
import com.product.springsecurity.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

   private JwtUtil jwtUtil;
   private AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public String welcome(){
        return "Welcome";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }
        catch(Exception e){
            throw new Exception("Invalid username or password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());

    }
}
