package org.example.bookshop.service;

import org.example.bookshop.dto.user.UserLoginRequestDto;
import org.example.bookshop.dto.user.UserLoginResponseDto;
import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserLoginResponseDto login(UserLoginRequestDto loginRequestDto);
}
