package com.product.springsecurity.service;

import com.product.springsecurity.dto.AuthRequest;
import com.product.springsecurity.dto.UserDto;

public interface SecurityService {
    public String register(UserDto userDto) throws Exception;
    public String login(AuthRequest authRequest);
}
