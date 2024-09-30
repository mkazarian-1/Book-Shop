package org.example.bookshop.service;

import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.model.User;

public interface UserService {
    UserResponseDto save(User user);
}
