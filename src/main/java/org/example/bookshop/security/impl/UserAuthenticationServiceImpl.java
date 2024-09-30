package org.example.bookshop.security.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.mapper.UserMapper;
import org.example.bookshop.security.UserAuthenticationService;
import org.example.bookshop.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        return userService.save(userMapper.toUser(requestDto));
    }
}
