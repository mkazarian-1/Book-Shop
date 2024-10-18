package org.example.bookshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.user.UserLoginRequestDto;
import org.example.bookshop.dto.user.UserLoginResponseDto;
import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.exception.RegistrationException;
import org.example.bookshop.mapper.UserMapper;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.RoleRepository;
import org.example.bookshop.repository.UserRepository;
import org.example.bookshop.security.JwtUtil;
import org.example.bookshop.service.ShoppingCartService;
import org.example.bookshop.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with this email already exist");
        }

        User user = userMapper.toUser(requestDto);
        userMapper.setPasswordAndRole(user, requestDto, passwordEncoder, roleRepository);
        user = userRepository.save(user);

        shoppingCartService.createShoppingCart(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto loginRequestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        return new UserLoginResponseDto(jwtUtil.generateToken(authentication.getName()));
    }
}
