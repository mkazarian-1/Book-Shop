package org.example.bookshop.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @NotBlank
    @Pattern(regexp = "^[^@]+@[^@]+\\.[^@]+$")
    private String email;
    @NotBlank
    private String password;
}
