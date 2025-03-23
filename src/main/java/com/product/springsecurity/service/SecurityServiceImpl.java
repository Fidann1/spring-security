package com.product.springsecurity.service;

import com.product.springsecurity.dto.AuthRequest;
import com.product.springsecurity.dto.UserDto;
import com.product.springsecurity.entity.UserEntity;
import com.product.springsecurity.repository.UserRepository;
import com.product.springsecurity.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;


@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;



    @Override
    public String register(UserDto userDto) throws Exception {
        UserEntity userEntity = userRepository.findByUsername(userDto.getUsername());
        if(userEntity != null) {
            throw new Exception("User already registered!");
        }
        String encodedPassword=passwordEncoder.encode(userDto.getPassword());
        userRepository.save(UserEntity.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(encodedPassword).build());
        return "User successfully registered!";
    }

    @Override
    public String login(AuthRequest authRequest) {
        try{
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtUtil.generateToken(authRequest.getUsername());}
        catch (Exception e) {
            return "Invalid username or password!";
        }

    }
}
