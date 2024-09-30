package org.example.bookshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.exception.RegistrationException;
import org.example.bookshop.mapper.UserMapper;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.UserRepository;
import org.example.bookshop.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RegistrationException("User with this email already exist");
        }

        return userMapper.toDto(userRepository.save(user));
    }
}
