package org.example.bookshop.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @NotBlank
    @Pattern(regexp = "^[^@]+@[^@]+\\.[^@]+$")
    private String email;
    @NotBlank
    @Size(max = 50)
    private String password;
}
