package org.example.bookshop.security;

import org.example.bookshop.dto.user.UserLoginRequestDto;
import org.example.bookshop.dto.user.UserLoginResponseDto;
import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;

public interface UserAuthenticationService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserLoginResponseDto login(UserLoginRequestDto loginRequestDto);
}
