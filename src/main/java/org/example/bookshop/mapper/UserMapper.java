package org.example.bookshop.mapper;

import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import org.example.bookshop.config.MapperConfig;
import org.example.bookshop.dto.user.UserRegistrationRequestDto;
import org.example.bookshop.dto.user.UserResponseDto;
import org.example.bookshop.model.Role;
import org.example.bookshop.model.User;
import org.example.bookshop.repository.RoleRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(UserRegistrationRequestDto requestDto);

    UserResponseDto toDto(User user);

    @AfterMapping
    default void setPasswordAndRole(@MappingTarget User user,
                                    UserRegistrationRequestDto requestDto,
                                    PasswordEncoder passwordEncoder,
                                    RoleRepository roleRepository) {
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Role userRole = roleRepository.findByRole(Role.RoleName.USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find role:" + Role.RoleName.USER));

        user.setRoles(Set.of(userRole));
    }
}

